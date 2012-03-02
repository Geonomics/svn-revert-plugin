package jenkins.plugins.svn_revert;

import static org.mockito.Mockito.verify;

import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class MessengerTest extends AbstractMockitoTestCase {

    @Mock
    private PrintStream logger;
    @Mock
    private Exception exception;
    private Messenger messenger;

    @Before
    public void setup() {
        messenger = new Messenger(logger);
    }

    @Test
    public void logsNotUnstableMessage() throws Exception {
        messenger.informBuildStatusNotUnstable();
        verify(logger).println(Messenger.BUILD_STATUS_NOT_UNSTABLE);
    }

    @Test
    public void logsPreviousNotSuccessMessage() throws Exception {
        messenger.informPreviousBuildStatusNotSuccess();
        verify(logger).println(Messenger.PREVIOUS_BUILD_STATUS_NOT_SUCCESS);
    }

    @Test
    public void logsNotSubversionScm() throws Exception {
        messenger.informNotSubversionSCM();
        verify(logger).println(Messenger.NOT_SUBVERSION_SCM);
    }

    @Test
    public void logsNoSvnAuthProvider() throws Exception {
        messenger.informNoSvnAuthProvider();
        verify(logger).println(Messenger.NO_SVN_AUTH_PROVIDER);
    }

    @Test
    public void logsWhenReverted() throws Exception {
        messenger.informReverted(Revisions.create(2, 2), "repo");
        verify(logger).format(Messenger.REVERTED_CHANGES, 1, 2, "repo");
    }

    @Test
    public void logsWhenRevertedOnMultipleRevisions() throws Exception {
        messenger.informReverted(Revisions.create(2, 4), "repo");
        verify(logger).format(Messenger.REVERTED_CHANGES, 1, 4, "repo");
    }

    @Test
    public void logsWhenFilesOutOfDate() throws Exception {
        messenger.informFilesToRevertOutOfDate();
        verify(logger).println(Messenger.FILES_TO_REVERT_OUT_OF_DATE);
    }

    @Test
    public void printsStackTrace() throws Exception {
        messenger.printStackTraceFor(exception);
        verify(exception).printStackTrace(logger);
    }

    @Test
    public void logsNoChanges() throws Exception {
        messenger.informNoChanges();
        verify(logger).println(Messenger.NO_CHANGES);
    }

    @Test
    public void logsWhenChangesOutsideWorkspace() throws Exception {
        messenger.informChangesOutsideWorkspace();
        verify(logger).println(Messenger.CHANGES_OUTSIDE_WORKSPACE);
    }

    @Test
    public void logsNothingRevertedBecauseOfSvnException() throws Exception {
        messenger.informNothingRevertedBecauseOf(exception);
        verify(logger).println(Messenger.SUBVERSION_EXCEPTION_DURING_REVERT);
        verify(exception).printStackTrace(logger);
    }
}
