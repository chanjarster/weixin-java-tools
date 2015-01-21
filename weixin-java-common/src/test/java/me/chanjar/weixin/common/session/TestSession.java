package me.chanjar.weixin.common.session;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class TestSession {

  @DataProvider
  public Object[][] getSessionManager() {
    return new Object[][] {
        new Object[] { new InMemorySessionManager() }
    };
  }


  @Test(dataProvider = "getSessionManager", expectedExceptions = IllegalStateException.class)
  public void testInvalidate(WxSessionManager sessionManager) {
    WxSession session = sessionManager.getSession("abc");
    session.invalidate();
    session.getAttributeNames();
  }

  @Test(dataProvider = "getSessionManager")
  public void testInvalidate2(InternalSessionManager sessionManager) {
    Assert.assertEquals(sessionManager.getActiveSessions(), 0);
    WxSession session = ((WxSessionManager) sessionManager).getSession("abc");
    Assert.assertEquals(sessionManager.getActiveSessions(), 1);
    session.invalidate();
    Assert.assertEquals(sessionManager.getActiveSessions(), 0);
  }

  @Test(dataProvider = "getSessionManager")
  public void testGetSession(WxSessionManager sessionManager) {
    WxSession session1 = sessionManager.getSession("abc");
    WxSession session2 = sessionManager.getSession("abc");
    Assert.assertTrue(session1.equals(session2));

    WxSession abc1 = sessionManager.getSession("abc1");
    Assert.assertFalse(session1.equals(abc1));

    WxSession abc1b = sessionManager.getSession("abc1", false);
    Assert.assertTrue(abc1.equals(abc1b));

    WxSession def = sessionManager.getSession("def", false);
    Assert.assertNull(def);
  }

  @Test(dataProvider = "getSessionManager")
  public void testBackgroundProcess(WxSessionManager sessionManager) throws InterruptedException {

    InternalSessionManager ism = (InternalSessionManager) sessionManager;
    ism.setMaxInactiveInterval(1);
    ism.setProcessExpiresFrequency(1);
    ism.setBackgroundProcessorDelay(1);

    Assert.assertEquals(ism.getActiveSessions(), 0);

    InternalSession abc = ism.createSession("abc");
    abc.endAccess();

    Thread.sleep(2000l);
    Assert.assertEquals(ism.getActiveSessions(), 0);

  }

}
