package cn.fly.verify;

import cn.fly.tools.proguard.PublicMemberKeeper;

/* loaded from: classes.dex */
public interface OAuthPageEventCallback extends PublicMemberKeeper {

    public interface AgreementClickedCallback {
        void handle();
    }

    public interface AgreementPageClosedCallback {
        void handle();
    }

    public interface CheckboxStatusChangedCallback {
        void handle(boolean z);
    }

    public interface CusAgreement1ClickedCallback {
        void handle();
    }

    public interface CusAgreement2ClickedCallback {
        void handle();
    }

    public interface CusAgreement3ClickedCallback {
        void handle();
    }

    public interface LoginBtnClickedCallback {
        void handle();
    }

    public static class OAuthPageEventResultCallback implements PublicMemberKeeper {
        private AgreementClickedCallback agreementClicked;
        private AgreementPageClosedCallback agreementPageClosed;
        private CheckboxStatusChangedCallback checkboxStatusChanged;
        private CusAgreement1ClickedCallback cusAgreement1Clicked;
        private CusAgreement2ClickedCallback cusAgreement2Clicked;
        private CusAgreement3ClickedCallback cusAgreement3Clicked;
        private LoginBtnClickedCallback loginBtnClicked;
        private PageOpenedCallback pageOpened;
        private PageClosedCallback pageclosed;

        public void agreementPageClosedCallback(AgreementPageClosedCallback agreementPageClosedCallback) {
            this.agreementPageClosed = agreementPageClosedCallback;
        }

        public void agreementPageOpenedCallback(AgreementClickedCallback agreementClickedCallback) {
            this.agreementClicked = agreementClickedCallback;
        }

        public void checkboxStatusChangedCallback(CheckboxStatusChangedCallback checkboxStatusChangedCallback) {
            this.checkboxStatusChanged = checkboxStatusChangedCallback;
        }

        public void cusAgreement1ClickedCallback(CusAgreement1ClickedCallback cusAgreement1ClickedCallback) {
            this.cusAgreement1Clicked = cusAgreement1ClickedCallback;
        }

        public void cusAgreement2ClickedCallback(CusAgreement2ClickedCallback cusAgreement2ClickedCallback) {
            this.cusAgreement2Clicked = cusAgreement2ClickedCallback;
        }

        public void cusAgreement3ClickedCallback(CusAgreement3ClickedCallback cusAgreement3ClickedCallback) {
            this.cusAgreement3Clicked = cusAgreement3ClickedCallback;
        }

        public void loginBtnClickedCallback(LoginBtnClickedCallback loginBtnClickedCallback) {
            this.loginBtnClicked = loginBtnClickedCallback;
        }

        public void pageCloseCallback(PageClosedCallback pageClosedCallback) {
            this.pageclosed = pageClosedCallback;
        }

        public void pageOpenCallback(PageOpenedCallback pageOpenedCallback) {
            this.pageOpened = pageOpenedCallback;
        }
    }

    public static class OAuthPageEventWrapper {
        public final AgreementClickedCallback agreementClicked;
        public final AgreementPageClosedCallback agreementPageClosed;
        public final CheckboxStatusChangedCallback checkboxStatusChanged;
        public final CusAgreement1ClickedCallback cusAgreement1Clicked;
        public final CusAgreement2ClickedCallback cusAgreement2Clicked;
        public final CusAgreement3ClickedCallback cusAgreement3Clicked;
        public final LoginBtnClickedCallback loginBtnClicked;
        public final PageOpenedCallback pageOpened;
        public final PageClosedCallback pageclosed;

        public OAuthPageEventWrapper(OAuthPageEventResultCallback oAuthPageEventResultCallback) {
            this.pageOpened = oAuthPageEventResultCallback.pageOpened;
            this.pageclosed = oAuthPageEventResultCallback.pageclosed;
            this.loginBtnClicked = oAuthPageEventResultCallback.loginBtnClicked;
            this.agreementClicked = oAuthPageEventResultCallback.agreementClicked;
            this.agreementPageClosed = oAuthPageEventResultCallback.agreementPageClosed;
            this.cusAgreement1Clicked = oAuthPageEventResultCallback.cusAgreement1Clicked;
            this.cusAgreement2Clicked = oAuthPageEventResultCallback.cusAgreement2Clicked;
            this.cusAgreement3Clicked = oAuthPageEventResultCallback.cusAgreement3Clicked;
            this.checkboxStatusChanged = oAuthPageEventResultCallback.checkboxStatusChanged;
        }
    }

    public interface PageClosedCallback {
        void handle();
    }

    public interface PageOpenedCallback {
        void handle();
    }

    void initCallback(OAuthPageEventResultCallback oAuthPageEventResultCallback);
}