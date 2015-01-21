package me.chanjar.weixin.common.session;

import me.chanjar.weixin.common.util.res.StringManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by qianjia on 15/1/21.
 */
public class SessionImpl implements WxSession, InternalSession {

  /**
   * The string manager for this package.
   */
  protected static final StringManager sm =
      StringManager.getManager(Constants.Package);

  // ------------------------------ WxSession
  protected Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();

  @Override
  public Object getAttribute(String name) {

    if (!isValidInternal())
      throw new IllegalStateException
          (sm.getString("sessionImpl.getAttribute.ise"));

    if (name == null) return null;

    return (attributes.get(name));
  }

  @Override
  public Enumeration<String> getAttributeNames() {
    if (!isValidInternal())
      throw new IllegalStateException
          (sm.getString("sessionImpl.getAttributeNames.ise"));

    Set<String> names = new HashSet<String>();
    names.addAll(attributes.keySet());
    return Collections.enumeration(names);
  }

  @Override
  public void setAttribute(String name, Object value) {
    // Name cannot be null
    if (name == null)
      throw new IllegalArgumentException
          (sm.getString("sessionImpl.setAttribute.namenull"));

    // Null value is the same as removeAttribute()
    if (value == null) {
      removeAttribute(name);
      return;
    }

    // Validate our current state
    if (!isValidInternal())
      throw new IllegalStateException(sm.getString(
          "sessionImpl.setAttribute.ise", getIdInternal()));

    attributes.put(name, value);

  }


  @Override
  public void removeAttribute(String name) {
    removeAttributeInternal(name);
  }


  @Override
  public void invalidate() {
    if (!isValidInternal())
      throw new IllegalStateException
          (sm.getString("sessionImpl.invalidate.ise"));

    // Cause this session to expire
    expire();

  }

  // ------------------------------ InternalSession
  /**
   * The session identifier of this Session.
   */
  protected String id = null;

  /**
   * Flag indicating whether this session is valid or not.
   */
  protected volatile boolean isValid = false;

  /**
   * Flag indicating whether this session is new or not.
   */
  protected boolean isNew = false;

  /**
   * We are currently processing a session expiration, so bypass
   * certain IllegalStateException tests.  NOTE:  This value is not
   * included in the serialized version of this object.
   */
  protected transient volatile boolean expiring = false;

  /**
   * The Manager with which this Session is associated.
   */
  protected transient InternalSessionManager manager = null;

  /**
   * Type array.
   */
  protected static final String EMPTY_ARRAY[] = new String[0];

  /**
   * The time this session was created, in milliseconds since midnight,
   * January 1, 1970 GMT.
   */
  protected long creationTime = 0L;

  /**
   * The current accessed time for this session.
   */
  protected volatile long thisAccessedTime = creationTime;

  /**
   * The last accessed time for this Session.
   */
  protected volatile long lastAccessedTime = creationTime;

  /**
   * The default maximum inactive interval for Sessions created by
   * this Manager.
   */
  protected int maxInactiveInterval = 30 * 60;

  /**
   * The facade associated with this session.  NOTE:  This value is not
   * included in the serialized version of this object.
   */
  protected transient InternalSessionFacade facade = null;


  public SessionImpl(InternalSessionManager manager) {
    this.manager = manager;
  }


  @Override
  public WxSession getSession() {

    if (facade == null){
      facade = new InternalSessionFacade(this);
    }
    return (facade);

  }

  /**
   * Return the <code>isValid</code> flag for this session without any expiration
   * check.
   */
  protected boolean isValidInternal() {
    return this.isValid;
  }

  /**
   * Set the <code>isValid</code> flag for this session.
   *
   * @param isValid The new value for the <code>isValid</code> flag
   */
  @Override
  public void setValid(boolean isValid) {
    this.isValid = isValid;
  }

  @Override
  public boolean isValid() {
    return isValid;
  }

  @Override
  public String getIdInternal() {
    return (this.id);
  }

  protected void removeAttributeInternal(String name) {
    // Avoid NPE
    if (name == null) return;

    // Remove this attribute from our collection
    attributes.remove(name);

  }

  @Override
  public void expire() {
    // Check to see if session has already been invalidated.
    // Do not check expiring at this point as expire should not return until
    // isValid is false
    if (!isValid)
      return;

    synchronized (this) {
      // Check again, now we are inside the sync so this code only runs once
      // Double check locking - isValid needs to be volatile
      // The check of expiring is to ensure that an infinite loop is not
      // entered as per bug 56339
      if (expiring || !isValid)
        return;

      if (manager == null)
        return;

      // Mark this session as "being expired"
      expiring = true;

      // Remove this session from our manager's active sessions
      manager.remove(this, true);


      // We have completed expire of this session
      setValid(false);
      expiring = false;

      // Unbind any objects associated with this session
      String keys[] = keys();
      for (int i = 0; i < keys.length; i++) {
        removeAttributeInternal(keys[i]);
      }
    }


  }


  @Override
  public void access() {

    this.thisAccessedTime = System.currentTimeMillis();

  }


  @Override
  public void setNew(boolean isNew) {

    this.isNew = isNew;

  }


  @Override
  public void setCreationTime(long time) {

    this.creationTime = time;
    this.lastAccessedTime = time;
    this.thisAccessedTime = time;

  }

  @Override
  public void setMaxInactiveInterval(int interval) {
    int oldMaxInactiveInterval = this.maxInactiveInterval;
    this.maxInactiveInterval = interval;
  }


  @Override
  public void setId(String id) {
    if ((this.id != null) && (manager != null))
      manager.remove(this);

    this.id = id;

    if (manager != null)
      manager.add(this);
  }

  /**
   * Return the names of all currently defined session attributes
   * as an array of Strings.  If there are no defined attributes, a
   * zero-length array is returned.
   */
  protected String[] keys() {

    return attributes.keySet().toArray(EMPTY_ARRAY);

  }

}
