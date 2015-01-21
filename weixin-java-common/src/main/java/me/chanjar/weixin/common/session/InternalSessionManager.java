package me.chanjar.weixin.common.session;

public interface InternalSessionManager {

  /**
   * Construct and return a new session object, based on the default
   * settings specified by this Manager's properties.  The session
   * id specified will be used as the session id.
   * If a new session cannot be created for any reason, return
   * <code>null</code>.
   *
   * @param sessionId The session id which should be used to create the
   *  new session; if <code>null</code>, a new session id will be
   *  generated
   * @exception IllegalStateException if a new session cannot be
   *  instantiated for any reason
   */
  public InternalSession createSession(String sessionId);

  /**
   * Remove this Session from the active Sessions for this Manager.
   *
   * @param session Session to be removed
   */
  public void remove(InternalSession session);

  /**
   * Remove this Session from the active Sessions for this Manager.
   *
   * @param session   Session to be removed
   * @param update    Should the expiration statistics be updated
   */
  public void remove(InternalSession session, boolean update);

  /**
   * Add this Session to the set of active Sessions for this Manager.
   *
   * @param session Session to be added
   */
  void add(InternalSession session);


  /**
   * Returns the number of active sessions
   *
   * @return number of sessions active
   */
  int getActiveSessions();
  /**
   * Get a session from the recycled ones or create a new empty one.
   * The PersistentManager manager does not need to create session data
   * because it reads it from the Store.
   */
  InternalSession createEmptySession();

  InternalSession[] findSessions();

  /**
   * Implements the Manager interface, direct call to processExpires
   */
  public void backgroundProcess();

  /**
   * Set the default maximum inactive interval (in seconds)
   * for Sessions created by this Manager.
   *
   * @param interval The new default value
   */
  void setMaxInactiveInterval(int interval);

  void setProcessExpiresFrequency(int processExpiresFrequency);

  void setBackgroundProcessorDelay(int backgroundProcessorDelay);
}
