package cn.fly.verify.datatype;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.widget.ImageView;
import android.widget.Toast;

/* loaded from: classes.dex */
public class LandUiSettings extends c {
    private boolean agreementAlignParentRight;
    private int agreementBaseTextColorId;
    private int agreementCMHKText;
    private String agreementCMHKTextString;
    private int agreementCmccText;
    private String agreementCmccTextString;
    private int agreementColorId;
    private int agreementCtccText;
    private String agreementCtccTextString;
    private int agreementCuccText;
    private String agreementCuccTextString;
    private boolean agreementGravityLeft;
    private boolean agreementHidden;
    private int agreementOffsetBottomY;
    private int agreementOffsetRightX;
    private int agreementOffsetX;
    private int agreementOffsetY;
    private Drawable agreementPageCloseImg;
    private int agreementPageCloseImgHeight;
    private boolean agreementPageCloseImgHidden;
    private int agreementPageCloseImgId;
    private ImageView.ScaleType agreementPageCloseImgScaleType;
    private int agreementPageCloseImgWidth;
    private int agreementPageTitle;
    private boolean agreementPageTitleHidden;
    private String agreementPageTitleString;
    private boolean agreementPageTitleTextBold;
    private int agreementPageTitleTextColor;
    private int agreementPageTitleTextSize;
    private SpannableString agreementText;
    private int agreementTextAnd1;
    private int agreementTextAnd2;
    private int agreementTextAnd3;
    private String agreementTextAndString1;
    private String agreementTextAndString2;
    private String agreementTextAndString3;
    private boolean agreementTextBold;
    private int agreementTextEnd;
    private String agreementTextEndString;
    private int agreementTextSize;
    private int agreementTextStart;
    private String agreementTextStartString;
    private boolean agreementTextWithUnderLine;
    private String agreementUncheckHintText;
    private int agreementUncheckHintTextId;
    private int agreementUncheckHintType;
    private Toast agreementUncheckToast;
    private boolean backgroundClickClose;
    private Drawable backgroundImg;
    private int backgroundImgId;
    private boolean bottomTranslateAnim;
    private boolean checkboxDefaultState;
    private boolean checkboxHidden;
    private Drawable checkboxImg;
    private int checkboxImgId;
    private int checkboxOffsetBottomY;
    private int checkboxOffsetRightX;
    private int checkboxOffsetX;
    private int checkboxOffsetY;
    private float checkboxScaleX;
    private float checkboxScaleY;
    private int cusAgreementColor1;
    private boolean cusAgreementColor1Seted;
    private int cusAgreementColor2;
    private boolean cusAgreementColor2Seted;
    private int cusAgreementColor3;
    private boolean cusAgreementColor3Seted;
    private String cusAgreementName1;
    private String cusAgreementName2;
    private String cusAgreementName3;
    private int cusAgreementNameId1;
    private int cusAgreementNameId2;
    private int cusAgreementNameId3;
    private int cusAgreementPageOneTitle;
    private String cusAgreementPageOneTitleString;
    private int cusAgreementPageThreeTitle;
    private String cusAgreementPageThreeTitleString;
    private int cusAgreementPageTwoTitle;
    private String cusAgreementPageTwoTitleString;
    private String cusAgreementUrl1;
    private String cusAgreementUrl2;
    private String cusAgreementUrl3;
    private boolean dialogAlignBottom;
    private int dialogBackground;
    private boolean dialogBackgroundClickClose;
    private Drawable dialogBackgroundDrawable;
    private int dialogHeight;
    private int dialogOffsetX;
    private int dialogOffsetY;
    private boolean dialogTheme;
    private int dialogWidth;
    private boolean fadeAnim;
    private int finishInAnim;
    private int finishOutAnim;
    private boolean fullScreen;
    private boolean hasFinishActivityAnim;
    private boolean hasStartActivityAnim;
    private boolean immersiveStatusTextColorBlack;
    private boolean immersiveTheme;
    private boolean loginBtnAlignParentRight;
    private int loginBtnHeight;
    private boolean loginBtnHidden;
    private Drawable loginBtnImg;
    private int loginBtnImgId;
    private int loginBtnOffsetBottomY;
    private int loginBtnOffsetRightX;
    private int loginBtnOffsetX;
    private int loginBtnOffsetY;
    private String loginBtnText;
    private boolean loginBtnTextBold;
    private int loginBtnTextColorId;
    private int loginBtnTextId;
    private int loginBtnTextSize;
    private int loginBtnWidth;
    private boolean logoAlignParentRight;
    private int logoHeight;
    private boolean logoHidden;
    private Drawable logoImg;
    private int logoImgId;
    private int logoOffsetBottomY;
    private int logoOffsetRightX;
    private int logoOffsetX;
    private int logoOffsetY;
    private int logoWidth;
    private Drawable navCloseImg;
    private int navCloseImgHeight;
    private boolean navCloseImgHidden;
    private int navCloseImgId;
    private int navCloseImgOffsetRightX;
    private int navCloseImgOffsetX;
    private int navCloseImgOffsetY;
    private ImageView.ScaleType navCloseImgScaleType;
    private int navCloseImgWidth;
    private int navColorId;
    private boolean navHidden;
    private String navText;
    private boolean navTextBold;
    private int navTextColorId;
    private int navTextId;
    private int navTextSize;
    private boolean navTransparent;
    private Boolean needFitsSystemWindow;
    private boolean numberAlignParentRight;
    private boolean numberBold;
    private int numberColorId;
    private boolean numberHidden;
    private int numberOffsetBottomY;
    private int numberOffsetRightX;
    private int numberOffsetX;
    private int numberOffsetY;
    private int numberSizeId;
    private boolean rightTranslateAnim;
    private boolean sloganAlignParentRight;
    private boolean sloganHidden;
    private int sloganOffsetBottomY;
    private int sloganOffsetRightX;
    private int sloganOffsetX;
    private int sloganOffsetY;
    private boolean sloganTextBold;
    private int sloganTextColor;
    private int sloganTextSize;
    private int startInAnim;
    private int startOutAnim;
    private boolean switchAccAlignParentRight;
    private int switchAccColorId;
    private boolean switchAccHidden;
    private int switchAccOffsetBottomY;
    private int switchAccOffsetRightX;
    private int switchAccOffsetX;
    private int switchAccOffsetY;
    private int switchAccText;
    private boolean switchAccTextBold;
    private int switchAccTextSize;
    private String switchAccTextString;
    private boolean translateAnim;
    private boolean virtualButtonTransparent;
    private boolean zoomAnim;

    public static class Builder extends c {
        private boolean dialogBackgroundClickClose;
        private Boolean needFitsSystemWindow;
        private int navColorId = -1;
        private int navTextId = -1;
        private String navText = "";
        private int navTextColorId = -13430989;
        private int navCloseImgId = -1;
        private Drawable navCloseImg = null;
        private int logoImgId = -1;
        private Drawable logoImg = null;
        private int numberColorId = -13430989;
        private int numberSizeId = -1;
        private int switchAccColorId = -11626753;
        private boolean switchAccHidden = false;
        private int checkboxImgId = -1;
        private Drawable checkboxImg = null;
        private boolean checkboxDefaultState = false;
        private int agreementColorId = -99762;
        private int cusAgreementNameId1 = -1;
        private String cusAgreementName1 = "";
        private String cusAgreementUrl1 = "";
        private int cusAgreementNameId2 = -1;
        private String cusAgreementName2 = "";
        private String cusAgreementUrl2 = "";
        private int cusAgreementNameId3 = -1;
        private String cusAgreementName3 = "";
        private String cusAgreementUrl3 = "";
        private int loginBtnImgId = -1;
        private Drawable loginBtnImg = null;
        private int loginBtnTextId = -1;
        private String loginBtnText = "";
        private int loginBtnTextColorId = -1;
        private boolean navHidden = false;
        private boolean navTransparent = true;
        private boolean navCloseImgHidden = false;
        private int backgroundImgId = -1;
        private Drawable backgroundImg = null;
        private boolean backgroundClickClose = false;
        private int logoWidth = -1;
        private int logoHeight = -1;
        private int logoOffsetX = -1;
        private int logoOffsetY = -1;
        private boolean logoHidden = false;
        private int numberOffsetX = -1;
        private int numberOffsetY = -1;
        private boolean checkboxHidden = false;
        private int agreementOffsetX = -1;
        private int agreementOffsetRightX = -1;
        private int agreementOffsetY = -1;
        private int agreementOffsetBottomY = -1;
        private boolean cusAgreementColor1Seted = false;
        private int cusAgreementColor1 = -99762;
        private boolean cusAgreementColor2Seted = false;
        private int cusAgreementColor2 = -99762;
        private boolean cusAgreementColor3Seted = false;
        private int cusAgreementColor3 = -99762;
        private int loginBtnTextSize = -1;
        private int loginBtnWidth = -1;
        private int loginBtnHeight = -1;
        private int loginBtnOffsetX = -1;
        private int loginBtnOffsetY = -1;
        private int sloganOffsetX = -1;
        private int sloganOffsetY = -1;
        private int sloganOffsetBottomY = -1;
        private int sloganTextSize = -1;
        private int sloganTextColor = -6710887;
        private boolean sloganHidden = false;
        private int switchAccTextSize = -1;
        private int switchAccOffsetX = -1;
        private int switchAccOffsetY = -1;
        private int switchAccText = -1;
        private String switchAccTextString = "";
        private boolean agreementGravityLeft = false;
        private int agreementBaseTextColorId = -13430989;
        private int agreementTextSize = -1;
        private boolean hasStartActivityAnim = false;
        private boolean hasFinishActivityAnim = false;
        private int startInAnim = -1;
        private int startOutAnim = -1;
        private int finishInAnim = -1;
        private int finishOutAnim = -1;
        private boolean immersiveTheme = false;
        private boolean immersiveStatusTextColorBlack = false;
        private boolean numberHidden = false;
        private boolean agreementHidden = false;
        private boolean loginBtnHidden = false;
        private int navTextSize = -1;
        private int navCloseImgWidth = -1;
        private int navCloseImgHeight = -1;
        private int navCloseImgOffsetX = -1;
        private int navCloseImgOffsetRightX = -1;
        private int navCloseImgOffsetY = -1;
        private int agreementCmccText = -1;
        private int agreementCuccText = -1;
        private int agreementCtccText = -1;
        private int agreementTextStart = -1;
        private int agreementTextAnd1 = -1;
        private int agreementTextAnd2 = -1;
        private int agreementTextAnd3 = -1;
        private int agreementTextEnd = -1;
        private String agreementCmccTextString = "";
        private String agreementCuccTextString = "";
        private String agreementCtccTextString = "";
        private String agreementTextStartString = "";
        private String agreementTextAndString1 = "";
        private String agreementTextAndString2 = "";
        private String agreementTextAndString3 = "";
        private String agreementTextEndString = "";
        private boolean dialogTheme = false;
        private boolean dialogAlignBottom = false;
        private int dialogOffsetX = -1;
        private int dialogOffsetY = -1;
        private int dialogWidth = -1;
        private int dialogHeight = -1;
        private int dialogBackground = -1;
        private Drawable dialogBackgroundDrawable = null;
        private boolean translateAnim = false;
        private boolean bottomTranslateAnim = false;
        private boolean rightTranslateAnim = false;
        private boolean zoomAnim = false;
        private boolean fadeAnim = false;
        private int logoOffsetBottomY = -1;
        private int numberOffsetBottomY = -1;
        private int switchAccOffsetBottomY = -1;
        private int loginBtnOffsetBottomY = -1;
        private int logoOffsetRightX = -1;
        private boolean logoAlignParentRight = false;
        private int numberOffsetRightX = -1;
        private boolean numberAlignParentRight = false;
        private int switchAccOffsetRightX = -1;
        private boolean switchAccAlignParentRight = false;
        private boolean agreementAlignParentRight = false;
        private int loginBtnOffsetRightX = -1;
        private boolean loginBtnAlignParentRight = false;
        private int sloganOffsetRightX = -1;
        private boolean sloganAlignParentRight = false;
        private SpannableString agreementText = null;
        private int agreementUncheckHintType = 0;
        private String agreementUncheckHintText = "";
        private int agreementUncheckHintTextId = -1;
        private boolean navTextBold = false;
        private boolean fullScreen = false;
        private boolean numberBold = false;
        private boolean switchAccTextBold = false;
        private boolean agreementTextBold = false;
        private boolean loginBtnTextBold = false;
        private boolean sloganTextBold = false;
        private boolean agreementTextWithUnderLine = false;
        private Toast agreementUncheckToast = null;
        private boolean virtualButtonTransparent = false;
        private String agreementPageTitleString = "";
        private String cusAgreementPageOneTitleString = "";
        private String cusAgreementPageTwoTitleString = "";
        private String cusAgreementPageThreeTitleString = "";
        private int agreementPageTitle = -1;
        private int cusAgreementPageOneTitle = -1;
        private int cusAgreementPageTwoTitle = -1;
        private int cusAgreementPageThreeTitle = -1;
        private int agreementPageCloseImgId = -1;
        private Drawable agreementPageCloseImg = null;
        private int checkboxOffsetX = 10;
        private int checkboxOffsetRightX = -1;
        private int checkboxOffsetY = -1;
        private int checkboxOffsetBottomY = -1;
        private ImageView.ScaleType navCloseImgScaleType = ImageView.ScaleType.CENTER_INSIDE;
        private boolean agreementPageCloseImgHidden = false;
        private int agreementPageCloseImgWidth = 50;
        private int agreementPageCloseImgHeight = 50;
        private ImageView.ScaleType agreementPageCloseImgScaleType = ImageView.ScaleType.CENTER_INSIDE;
        private int agreementPageTitleTextSize = 16;
        private int agreementPageTitleTextColor = -13430989;
        private boolean agreementPageTitleTextBold = false;
        private boolean agreementPageTitleHidden = false;
        private float checkboxScaleX = 1.0f;
        private float checkboxScaleY = 1.0f;

        public LandUiSettings build() {
            return new LandUiSettings(this);
        }

        public Boolean getNeedFitsSystemWindow() {
            return this.needFitsSystemWindow;
        }

        public Builder setAgreementAlignParentRight(boolean z) {
            this.agreementAlignParentRight = z;
            return this;
        }

        public Builder setAgreementBaseTextColorId(int i) {
            this.agreementBaseTextColorId = i;
            return this;
        }

        public Builder setAgreementCmccText(int i) {
            this.agreementCmccText = i;
            return this;
        }

        public Builder setAgreementCmccText(String str) {
            this.agreementCmccTextString = str;
            return this;
        }

        public Builder setAgreementColorId(int i) {
            this.agreementColorId = i;
            return this;
        }

        public Builder setAgreementCtccText(int i) {
            this.agreementCtccText = i;
            return this;
        }

        public Builder setAgreementCtccText(String str) {
            this.agreementCtccTextString = str;
            return this;
        }

        public Builder setAgreementCuccText(int i) {
            this.agreementCuccText = i;
            return this;
        }

        public Builder setAgreementCuccText(String str) {
            this.agreementCuccTextString = str;
            return this;
        }

        public Builder setAgreementGravityLeft(boolean z) {
            this.agreementGravityLeft = z;
            return this;
        }

        public Builder setAgreementHidden(boolean z) {
            this.agreementHidden = z;
            return this;
        }

        public Builder setAgreementOffsetBottomY(int i) {
            this.agreementOffsetBottomY = i;
            return this;
        }

        public Builder setAgreementOffsetRightX(int i) {
            this.agreementOffsetRightX = i;
            return this;
        }

        public Builder setAgreementOffsetX(int i) {
            this.agreementOffsetX = i;
            return this;
        }

        public Builder setAgreementOffsetY(int i) {
            this.agreementOffsetY = i;
            return this;
        }

        public Builder setAgreementPageCloseImg(int i) {
            this.agreementPageCloseImgId = i;
            return this;
        }

        public Builder setAgreementPageCloseImg(Drawable drawable) {
            this.agreementPageCloseImg = drawable;
            return this;
        }

        public Builder setAgreementPageCloseImgHeight(int i) {
            this.agreementPageCloseImgHeight = i;
            return this;
        }

        public Builder setAgreementPageCloseImgHidden(boolean z) {
            this.agreementPageCloseImgHidden = z;
            return this;
        }

        public Builder setAgreementPageCloseImgScaleType(ImageView.ScaleType scaleType) {
            this.agreementPageCloseImgScaleType = scaleType;
            return this;
        }

        public Builder setAgreementPageCloseImgWidth(int i) {
            this.agreementPageCloseImgWidth = i;
            return this;
        }

        public Builder setAgreementPageTitle(int i) {
            this.agreementPageTitle = i;
            return this;
        }

        public Builder setAgreementPageTitle(String str) {
            this.agreementPageTitleString = str;
            return this;
        }

        public Builder setAgreementPageTitleHidden(boolean z) {
            this.agreementPageTitleHidden = z;
            return this;
        }

        public Builder setAgreementPageTitleTextBold(boolean z) {
            this.agreementPageTitleTextBold = z;
            return this;
        }

        public Builder setAgreementPageTitleTextColor(int i) {
            this.agreementPageTitleTextColor = i;
            return this;
        }

        public Builder setAgreementPageTitleTextSize(int i) {
            this.agreementPageTitleTextSize = i;
            return this;
        }

        public Builder setAgreementText(SpannableString spannableString) {
            this.agreementText = spannableString;
            return this;
        }

        public Builder setAgreementTextAnd1(int i) {
            this.agreementTextAnd1 = i;
            return this;
        }

        public Builder setAgreementTextAnd1(String str) {
            this.agreementTextAndString1 = str;
            return this;
        }

        public Builder setAgreementTextAnd2(int i) {
            this.agreementTextAnd2 = i;
            return this;
        }

        public Builder setAgreementTextAnd2(String str) {
            this.agreementTextAndString2 = str;
            return this;
        }

        public Builder setAgreementTextAnd3(int i) {
            this.agreementTextAnd3 = i;
            return this;
        }

        public Builder setAgreementTextAnd3(String str) {
            this.agreementTextAndString3 = str;
            return this;
        }

        public Builder setAgreementTextBold(boolean z) {
            this.agreementTextBold = z;
            return this;
        }

        public Builder setAgreementTextEnd(int i) {
            this.agreementTextEnd = i;
            return this;
        }

        public Builder setAgreementTextEnd(String str) {
            this.agreementTextEndString = str;
            return this;
        }

        public Builder setAgreementTextSize(int i) {
            this.agreementTextSize = i;
            return this;
        }

        public Builder setAgreementTextStart(int i) {
            this.agreementTextStart = i;
            return this;
        }

        public Builder setAgreementTextStart(String str) {
            this.agreementTextStartString = str;
            return this;
        }

        public Builder setAgreementTextWithUnderLine(boolean z) {
            this.agreementTextWithUnderLine = z;
            return this;
        }

        public Builder setAgreementUncheckHintText(int i) {
            this.agreementUncheckHintTextId = i;
            return this;
        }

        public Builder setAgreementUncheckHintText(String str) {
            this.agreementUncheckHintText = str;
            return this;
        }

        public Builder setAgreementUncheckHintType(int i) {
            this.agreementUncheckHintType = i;
            return this;
        }

        public Builder setAgreementUncheckToast(Toast toast) {
            this.agreementUncheckToast = toast;
            return this;
        }

        public Builder setBackgroundClickClose(boolean z) {
            this.backgroundClickClose = z;
            return this;
        }

        public Builder setBackgroundImgId(int i) {
            this.backgroundImgId = i;
            return this;
        }

        public Builder setBackgroundImgId(Drawable drawable) {
            this.backgroundImg = drawable;
            return this;
        }

        public Builder setBottomTranslateAnim(boolean z) {
            this.bottomTranslateAnim = z;
            return this;
        }

        public Builder setCheckboxDefaultState(boolean z) {
            this.checkboxDefaultState = z;
            return this;
        }

        public Builder setCheckboxHidden(boolean z) {
            this.checkboxHidden = z;
            return this;
        }

        public Builder setCheckboxImgId(int i) {
            this.checkboxImgId = i;
            return this;
        }

        public Builder setCheckboxImgId(Drawable drawable) {
            this.checkboxImg = drawable;
            return this;
        }

        public Builder setCheckboxOffsetBottomY(int i) {
            this.checkboxOffsetBottomY = i;
            return this;
        }

        public Builder setCheckboxOffsetRightX(int i) {
            this.checkboxOffsetRightX = i;
            return this;
        }

        public Builder setCheckboxOffsetX(int i) {
            this.checkboxOffsetX = i;
            return this;
        }

        public Builder setCheckboxOffsetY(int i) {
            this.checkboxOffsetY = i;
            return this;
        }

        public Builder setCheckboxScaleX(float f) {
            this.checkboxScaleX = f;
            return this;
        }

        public Builder setCheckboxScaleY(float f) {
            this.checkboxScaleY = f;
            return this;
        }

        public Builder setCusAgreementColor1(int i) {
            this.cusAgreementColor1Seted = true;
            this.cusAgreementColor1 = i;
            return this;
        }

        public Builder setCusAgreementColor2(int i) {
            this.cusAgreementColor2Seted = true;
            this.cusAgreementColor2 = i;
            return this;
        }

        public Builder setCusAgreementColor3(int i) {
            this.cusAgreementColor3Seted = true;
            this.cusAgreementColor3 = i;
            return this;
        }

        public Builder setCusAgreementNameId1(int i) {
            this.cusAgreementNameId1 = i;
            return this;
        }

        public Builder setCusAgreementNameId1(String str) {
            this.cusAgreementName1 = str;
            return this;
        }

        public Builder setCusAgreementNameId2(int i) {
            this.cusAgreementNameId2 = i;
            return this;
        }

        public Builder setCusAgreementNameId2(String str) {
            this.cusAgreementName2 = str;
            return this;
        }

        public Builder setCusAgreementNameId3(int i) {
            this.cusAgreementNameId3 = i;
            return this;
        }

        public Builder setCusAgreementNameId3(String str) {
            this.cusAgreementName3 = str;
            return this;
        }

        public Builder setCusAgreementPageOneTitle(int i) {
            this.cusAgreementPageOneTitle = i;
            return this;
        }

        public Builder setCusAgreementPageOneTitle(String str) {
            this.cusAgreementPageOneTitleString = str;
            return this;
        }

        public Builder setCusAgreementPageThreeTitle(int i) {
            this.cusAgreementPageThreeTitle = i;
            return this;
        }

        public Builder setCusAgreementPageThreeTitle(String str) {
            this.cusAgreementPageThreeTitleString = str;
            return this;
        }

        public Builder setCusAgreementPageTwoTitle(int i) {
            this.cusAgreementPageTwoTitle = i;
            return this;
        }

        public Builder setCusAgreementPageTwoTitle(String str) {
            this.cusAgreementPageTwoTitleString = str;
            return this;
        }

        public Builder setCusAgreementUrl1(String str) {
            this.cusAgreementUrl1 = str;
            return this;
        }

        public Builder setCusAgreementUrl2(String str) {
            this.cusAgreementUrl2 = str;
            return this;
        }

        public Builder setCusAgreementUrl3(String str) {
            this.cusAgreementUrl3 = str;
            return this;
        }

        public Builder setDialogAlignBottom(boolean z) {
            this.dialogAlignBottom = z;
            return this;
        }

        public Builder setDialogHeight(int i) {
            this.dialogHeight = i;
            return this;
        }

        public Builder setDialogMaskBackground(int i) {
            this.dialogBackground = i;
            return this;
        }

        public Builder setDialogMaskBackground(Drawable drawable) {
            this.dialogBackgroundDrawable = drawable;
            return this;
        }

        public Builder setDialogMaskBackgroundClickClose(boolean z) {
            this.dialogBackgroundClickClose = z;
            return this;
        }

        public Builder setDialogOffsetX(int i) {
            this.dialogOffsetX = i;
            return this;
        }

        public Builder setDialogOffsetY(int i) {
            this.dialogOffsetY = i;
            return this;
        }

        public Builder setDialogTheme(boolean z) {
            this.dialogTheme = z;
            return this;
        }

        public Builder setDialogWidth(int i) {
            this.dialogWidth = i;
            return this;
        }

        public Builder setFadeAnim(boolean z) {
            this.fadeAnim = z;
            return this;
        }

        public Builder setFinishActivityTransitionAnim(int i, int i2) {
            this.hasFinishActivityAnim = true;
            this.finishInAnim = i;
            this.finishOutAnim = i2;
            return this;
        }

        public Builder setFullScreen(boolean z) {
            this.fullScreen = z;
            return this;
        }

        public Builder setImmersiveStatusTextColorBlack(boolean z) {
            this.immersiveStatusTextColorBlack = z;
            return this;
        }

        public Builder setImmersiveTheme(boolean z) {
            this.immersiveTheme = z;
            return this;
        }

        public Builder setLoginBtnAlignParentRight(boolean z) {
            this.loginBtnAlignParentRight = z;
            return this;
        }

        public Builder setLoginBtnHeight(int i) {
            this.loginBtnHeight = i;
            return this;
        }

        public Builder setLoginBtnHidden(boolean z) {
            this.loginBtnHidden = z;
            return this;
        }

        public Builder setLoginBtnImgId(int i) {
            this.loginBtnImgId = i;
            return this;
        }

        public Builder setLoginBtnImgId(Drawable drawable) {
            this.loginBtnImg = drawable;
            return this;
        }

        public Builder setLoginBtnOffsetBottomY(int i) {
            this.loginBtnOffsetBottomY = i;
            return this;
        }

        public Builder setLoginBtnOffsetRightX(int i) {
            this.loginBtnOffsetRightX = i;
            return this;
        }

        public Builder setLoginBtnOffsetX(int i) {
            this.loginBtnOffsetX = i;
            return this;
        }

        public Builder setLoginBtnOffsetY(int i) {
            this.loginBtnOffsetY = i;
            return this;
        }

        public Builder setLoginBtnTextBold(boolean z) {
            this.loginBtnTextBold = z;
            return this;
        }

        public Builder setLoginBtnTextColorId(int i) {
            this.loginBtnTextColorId = i;
            return this;
        }

        public Builder setLoginBtnTextId(int i) {
            this.loginBtnTextId = i;
            return this;
        }

        public Builder setLoginBtnTextId(String str) {
            this.loginBtnText = str;
            return this;
        }

        public Builder setLoginBtnTextSize(int i) {
            this.loginBtnTextSize = i;
            return this;
        }

        public Builder setLoginBtnWidth(int i) {
            this.loginBtnWidth = i;
            return this;
        }

        public Builder setLogoAlignParentRight(boolean z) {
            this.logoAlignParentRight = z;
            return this;
        }

        public Builder setLogoHeight(int i) {
            this.logoHeight = i;
            return this;
        }

        public Builder setLogoHidden(boolean z) {
            this.logoHidden = z;
            return this;
        }

        public Builder setLogoImgId(int i) {
            this.logoImgId = i;
            return this;
        }

        public Builder setLogoImgId(Drawable drawable) {
            this.logoImg = drawable;
            return this;
        }

        public Builder setLogoOffsetBottomY(int i) {
            this.logoOffsetBottomY = i;
            return this;
        }

        public Builder setLogoOffsetRightX(int i) {
            this.logoOffsetRightX = i;
            return this;
        }

        public Builder setLogoOffsetX(int i) {
            this.logoOffsetX = i;
            return this;
        }

        public Builder setLogoOffsetY(int i) {
            this.logoOffsetY = i;
            return this;
        }

        public Builder setLogoWidth(int i) {
            this.logoWidth = i;
            return this;
        }

        public Builder setNavCloseImgHeight(int i) {
            this.navCloseImgHeight = i;
            return this;
        }

        public Builder setNavCloseImgHidden(boolean z) {
            this.navCloseImgHidden = z;
            return this;
        }

        public Builder setNavCloseImgId(int i) {
            this.navCloseImgId = i;
            return this;
        }

        public Builder setNavCloseImgId(Drawable drawable) {
            this.navCloseImg = drawable;
            return this;
        }

        public Builder setNavCloseImgOffsetRightX(int i) {
            this.navCloseImgOffsetRightX = i;
            return this;
        }

        public Builder setNavCloseImgOffsetX(int i) {
            this.navCloseImgOffsetX = i;
            return this;
        }

        public Builder setNavCloseImgOffsetY(int i) {
            this.navCloseImgOffsetY = i;
            return this;
        }

        public Builder setNavCloseImgScaleType(ImageView.ScaleType scaleType) {
            this.navCloseImgScaleType = scaleType;
            return this;
        }

        public Builder setNavCloseImgWidth(int i) {
            this.navCloseImgWidth = i;
            return this;
        }

        public Builder setNavColorId(int i) {
            this.navColorId = i;
            return this;
        }

        public Builder setNavHidden(boolean z) {
            this.navHidden = z;
            return this;
        }

        public Builder setNavTextBold(boolean z) {
            this.navTextBold = z;
            return this;
        }

        public Builder setNavTextColorId(int i) {
            this.navTextColorId = i;
            return this;
        }

        public Builder setNavTextId(int i) {
            this.navTextId = i;
            return this;
        }

        public Builder setNavTextId(String str) {
            this.navText = str;
            return this;
        }

        public Builder setNavTextSize(int i) {
            this.navTextSize = i;
            return this;
        }

        public Builder setNavTransparent(boolean z) {
            this.navTransparent = z;
            return this;
        }

        public Builder setNeedFitsSystemWindow(Boolean bool) {
            this.needFitsSystemWindow = bool;
            return this;
        }

        public Builder setNumberAlignParentRight(boolean z) {
            this.numberAlignParentRight = z;
            return this;
        }

        public Builder setNumberBold(boolean z) {
            this.numberBold = z;
            return this;
        }

        public Builder setNumberColorId(int i) {
            this.numberColorId = i;
            return this;
        }

        public Builder setNumberHidden(boolean z) {
            this.numberHidden = z;
            return this;
        }

        public Builder setNumberOffsetBottomY(int i) {
            this.numberOffsetBottomY = i;
            return this;
        }

        public Builder setNumberOffsetRightX(int i) {
            this.numberOffsetRightX = i;
            return this;
        }

        public Builder setNumberOffsetX(int i) {
            this.numberOffsetX = i;
            return this;
        }

        public Builder setNumberOffsetY(int i) {
            this.numberOffsetY = i;
            return this;
        }

        public Builder setNumberSizeId(int i) {
            this.numberSizeId = i;
            return this;
        }

        public Builder setRightTranslateAnim(boolean z) {
            this.rightTranslateAnim = z;
            return this;
        }

        public Builder setSloganAlignParentRight(boolean z) {
            this.sloganAlignParentRight = z;
            return this;
        }

        public Builder setSloganHidden(boolean z) {
            this.sloganHidden = z;
            return this;
        }

        public Builder setSloganOffsetBottomY(int i) {
            this.sloganOffsetBottomY = i;
            return this;
        }

        public Builder setSloganOffsetRightX(int i) {
            this.sloganOffsetRightX = i;
            return this;
        }

        public Builder setSloganOffsetX(int i) {
            this.sloganOffsetX = i;
            return this;
        }

        public Builder setSloganOffsetY(int i) {
            this.sloganOffsetY = i;
            return this;
        }

        public Builder setSloganTextBold(boolean z) {
            this.sloganTextBold = z;
            return this;
        }

        public Builder setSloganTextColor(int i) {
            this.sloganTextColor = i;
            return this;
        }

        public Builder setSloganTextSize(int i) {
            this.sloganTextSize = i;
            return this;
        }

        public Builder setStartActivityTransitionAnim(int i, int i2) {
            this.hasStartActivityAnim = true;
            this.startInAnim = i;
            this.startOutAnim = i2;
            return this;
        }

        public Builder setSwitchAccAlignParentRight(boolean z) {
            this.switchAccAlignParentRight = z;
            return this;
        }

        public Builder setSwitchAccColorId(int i) {
            this.switchAccColorId = i;
            return this;
        }

        public Builder setSwitchAccHidden(boolean z) {
            this.switchAccHidden = z;
            return this;
        }

        public Builder setSwitchAccOffsetBottomY(int i) {
            this.switchAccOffsetBottomY = i;
            return this;
        }

        public Builder setSwitchAccOffsetRightX(int i) {
            this.switchAccOffsetRightX = i;
            return this;
        }

        public Builder setSwitchAccOffsetX(int i) {
            this.switchAccOffsetX = i;
            return this;
        }

        public Builder setSwitchAccOffsetY(int i) {
            this.switchAccOffsetY = i;
            return this;
        }

        public Builder setSwitchAccText(int i) {
            this.switchAccText = i;
            return this;
        }

        public Builder setSwitchAccText(String str) {
            this.switchAccTextString = str;
            return this;
        }

        public Builder setSwitchAccTextBold(boolean z) {
            this.switchAccTextBold = z;
            return this;
        }

        public Builder setSwitchAccTextSize(int i) {
            this.switchAccTextSize = i;
            return this;
        }

        public Builder setTranslateAnim(boolean z) {
            this.translateAnim = z;
            return this;
        }

        public Builder setVirtualButtonTransparent(boolean z) {
            this.virtualButtonTransparent = z;
            return this;
        }

        public Builder setZoomAnim(boolean z) {
            this.zoomAnim = z;
            return this;
        }
    }

    private LandUiSettings(Builder builder) {
        this.navColorId = builder.navColorId;
        this.navTextId = builder.navTextId;
        this.navTextColorId = builder.navTextColorId;
        this.navCloseImgId = builder.navCloseImgId;
        this.logoImgId = builder.logoImgId;
        this.numberColorId = builder.numberColorId;
        this.numberSizeId = builder.numberSizeId;
        this.switchAccColorId = builder.switchAccColorId;
        this.switchAccHidden = builder.switchAccHidden;
        this.checkboxImgId = builder.checkboxImgId;
        this.checkboxDefaultState = builder.checkboxDefaultState;
        this.agreementColorId = builder.agreementColorId;
        this.cusAgreementNameId1 = builder.cusAgreementNameId1;
        this.cusAgreementUrl1 = builder.cusAgreementUrl1;
        this.cusAgreementNameId2 = builder.cusAgreementNameId2;
        this.cusAgreementUrl2 = builder.cusAgreementUrl2;
        this.cusAgreementNameId3 = builder.cusAgreementNameId3;
        this.cusAgreementUrl3 = builder.cusAgreementUrl3;
        this.loginBtnImgId = builder.loginBtnImgId;
        this.loginBtnTextId = builder.loginBtnTextId;
        this.loginBtnTextColorId = builder.loginBtnTextColorId;
        this.navHidden = builder.navHidden;
        this.navTransparent = builder.navTransparent;
        this.navCloseImgHidden = builder.navCloseImgHidden;
        this.backgroundImgId = builder.backgroundImgId;
        this.backgroundClickClose = builder.backgroundClickClose;
        this.logoWidth = builder.logoWidth;
        this.logoHeight = builder.logoHeight;
        this.logoOffsetX = builder.logoOffsetX;
        this.logoOffsetY = builder.logoOffsetY;
        this.logoHidden = builder.logoHidden;
        this.numberOffsetX = builder.numberOffsetX;
        this.numberOffsetY = builder.numberOffsetY;
        this.switchAccTextSize = builder.switchAccTextSize;
        this.switchAccOffsetX = builder.switchAccOffsetX;
        this.switchAccOffsetY = builder.switchAccOffsetY;
        this.switchAccText = builder.switchAccText;
        this.checkboxHidden = builder.checkboxHidden;
        this.agreementOffsetX = builder.agreementOffsetX;
        this.agreementOffsetRightX = builder.agreementOffsetRightX;
        this.agreementOffsetY = builder.agreementOffsetY;
        this.agreementOffsetBottomY = builder.agreementOffsetBottomY;
        this.cusAgreementColor1 = builder.cusAgreementColor1;
        this.cusAgreementColor2 = builder.cusAgreementColor2;
        this.cusAgreementColor3 = builder.cusAgreementColor3;
        this.loginBtnTextSize = builder.loginBtnTextSize;
        this.loginBtnWidth = builder.loginBtnWidth;
        this.loginBtnHeight = builder.loginBtnHeight;
        this.loginBtnOffsetX = builder.loginBtnOffsetX;
        this.loginBtnOffsetY = builder.loginBtnOffsetY;
        this.sloganOffsetX = builder.sloganOffsetX;
        this.sloganOffsetY = builder.sloganOffsetY;
        this.sloganOffsetBottomY = builder.sloganOffsetBottomY;
        this.sloganTextSize = builder.sloganTextSize;
        this.sloganTextColor = builder.sloganTextColor;
        this.sloganHidden = builder.sloganHidden;
        this.agreementGravityLeft = builder.agreementGravityLeft;
        this.agreementBaseTextColorId = builder.agreementBaseTextColorId;
        this.agreementTextSize = builder.agreementTextSize;
        this.translateAnim = builder.translateAnim;
        this.bottomTranslateAnim = builder.bottomTranslateAnim;
        this.rightTranslateAnim = builder.rightTranslateAnim;
        this.zoomAnim = builder.zoomAnim;
        this.fadeAnim = builder.fadeAnim;
        this.hasStartActivityAnim = builder.hasStartActivityAnim;
        this.hasFinishActivityAnim = builder.hasFinishActivityAnim;
        this.startInAnim = builder.startInAnim;
        this.startOutAnim = builder.startOutAnim;
        this.finishInAnim = builder.finishInAnim;
        this.finishOutAnim = builder.finishOutAnim;
        this.immersiveTheme = builder.immersiveTheme;
        this.immersiveStatusTextColorBlack = builder.immersiveStatusTextColorBlack;
        this.numberHidden = builder.numberHidden;
        this.agreementHidden = builder.agreementHidden;
        this.loginBtnHidden = builder.loginBtnHidden;
        this.navTextSize = builder.navTextSize;
        this.navCloseImgWidth = builder.navCloseImgWidth;
        this.navCloseImgHeight = builder.navCloseImgHeight;
        this.navCloseImgOffsetX = builder.navCloseImgOffsetX;
        this.navCloseImgOffsetRightX = builder.navCloseImgOffsetRightX;
        this.navCloseImgOffsetY = builder.navCloseImgOffsetY;
        this.agreementTextStart = builder.agreementTextStart;
        this.agreementTextAnd1 = builder.agreementTextAnd1;
        this.agreementTextAnd2 = builder.agreementTextAnd2;
        this.agreementTextAnd3 = builder.agreementTextAnd3;
        this.agreementTextEnd = builder.agreementTextEnd;
        this.agreementCmccText = builder.agreementCmccText;
        this.agreementCuccText = builder.agreementCuccText;
        this.agreementCtccText = builder.agreementCtccText;
        this.dialogTheme = builder.dialogTheme;
        this.dialogAlignBottom = builder.dialogAlignBottom;
        this.dialogOffsetX = builder.dialogOffsetX;
        this.dialogOffsetY = builder.dialogOffsetY;
        this.dialogWidth = builder.dialogWidth;
        this.dialogHeight = builder.dialogHeight;
        this.dialogBackground = builder.dialogBackground;
        this.dialogBackgroundClickClose = builder.dialogBackgroundClickClose;
        this.logoOffsetBottomY = builder.logoOffsetBottomY;
        this.numberOffsetBottomY = builder.numberOffsetBottomY;
        this.switchAccOffsetBottomY = builder.switchAccOffsetBottomY;
        this.loginBtnOffsetBottomY = builder.loginBtnOffsetBottomY;
        this.logoOffsetRightX = builder.logoOffsetRightX;
        this.logoAlignParentRight = builder.logoAlignParentRight;
        this.numberOffsetRightX = builder.numberOffsetRightX;
        this.numberAlignParentRight = builder.numberAlignParentRight;
        this.switchAccOffsetRightX = builder.switchAccOffsetRightX;
        this.switchAccAlignParentRight = builder.switchAccAlignParentRight;
        this.agreementAlignParentRight = builder.agreementAlignParentRight;
        this.loginBtnOffsetRightX = builder.loginBtnOffsetRightX;
        this.loginBtnAlignParentRight = builder.loginBtnAlignParentRight;
        this.sloganOffsetRightX = builder.sloganOffsetRightX;
        this.sloganAlignParentRight = builder.sloganAlignParentRight;
        this.navText = builder.navText;
        this.cusAgreementName1 = builder.cusAgreementName1;
        this.cusAgreementName2 = builder.cusAgreementName2;
        this.cusAgreementName3 = builder.cusAgreementName3;
        this.loginBtnText = builder.loginBtnText;
        this.switchAccTextString = builder.switchAccTextString;
        this.agreementCmccTextString = builder.agreementCmccTextString;
        this.agreementCuccTextString = builder.agreementCuccTextString;
        this.agreementCtccTextString = builder.agreementCtccTextString;
        this.agreementTextStartString = builder.agreementTextStartString;
        this.agreementTextAndString1 = builder.agreementTextAndString1;
        this.agreementTextAndString2 = builder.agreementTextAndString2;
        this.agreementTextAndString3 = builder.agreementTextAndString3;
        this.agreementTextEndString = builder.agreementTextEndString;
        this.navCloseImg = builder.navCloseImg;
        this.logoImg = builder.logoImg;
        this.checkboxImg = builder.checkboxImg;
        this.loginBtnImg = builder.loginBtnImg;
        this.backgroundImg = builder.backgroundImg;
        this.dialogBackgroundDrawable = builder.dialogBackgroundDrawable;
        this.cusAgreementColor1Seted = builder.cusAgreementColor1Seted;
        this.cusAgreementColor2Seted = builder.cusAgreementColor2Seted;
        this.cusAgreementColor3Seted = builder.cusAgreementColor3Seted;
        this.agreementText = builder.agreementText;
        this.agreementUncheckHintType = builder.agreementUncheckHintType;
        this.agreementUncheckHintText = builder.agreementUncheckHintText;
        this.agreementUncheckHintTextId = builder.agreementUncheckHintTextId;
        this.navTextBold = builder.navTextBold;
        this.fullScreen = builder.fullScreen;
        this.numberBold = builder.numberBold;
        this.switchAccTextBold = builder.switchAccTextBold;
        this.agreementTextBold = builder.agreementTextBold;
        this.loginBtnTextBold = builder.loginBtnTextBold;
        this.sloganTextBold = builder.sloganTextBold;
        this.agreementTextWithUnderLine = builder.agreementTextWithUnderLine;
        this.agreementUncheckToast = builder.agreementUncheckToast;
        this.virtualButtonTransparent = builder.virtualButtonTransparent;
        this.agreementPageTitleString = builder.agreementPageTitleString;
        this.cusAgreementPageOneTitleString = builder.cusAgreementPageOneTitleString;
        this.cusAgreementPageTwoTitleString = builder.cusAgreementPageTwoTitleString;
        this.cusAgreementPageThreeTitleString = builder.cusAgreementPageThreeTitleString;
        this.agreementPageTitle = builder.agreementPageTitle;
        this.cusAgreementPageOneTitle = builder.cusAgreementPageOneTitle;
        this.cusAgreementPageTwoTitle = builder.cusAgreementPageTwoTitle;
        this.cusAgreementPageThreeTitle = builder.cusAgreementPageThreeTitle;
        this.agreementPageCloseImgId = builder.agreementPageCloseImgId;
        this.agreementPageCloseImg = builder.agreementPageCloseImg;
        this.navCloseImgScaleType = builder.navCloseImgScaleType;
        this.checkboxOffsetX = builder.checkboxOffsetX;
        this.checkboxOffsetRightX = builder.checkboxOffsetRightX;
        this.checkboxOffsetY = builder.checkboxOffsetY;
        this.checkboxOffsetBottomY = builder.checkboxOffsetBottomY;
        this.agreementPageCloseImgHidden = builder.agreementPageCloseImgHidden;
        this.agreementPageCloseImgWidth = builder.agreementPageCloseImgWidth;
        this.agreementPageCloseImgHeight = builder.agreementPageCloseImgHeight;
        this.agreementPageCloseImgScaleType = builder.agreementPageCloseImgScaleType;
        this.agreementPageTitleTextSize = builder.agreementPageTitleTextSize;
        this.agreementPageTitleTextColor = builder.agreementPageTitleTextColor;
        this.agreementPageTitleTextBold = builder.agreementPageTitleTextBold;
        this.agreementPageTitleHidden = builder.agreementPageTitleHidden;
        this.checkboxScaleX = builder.checkboxScaleX;
        this.checkboxScaleY = builder.checkboxScaleY;
        this.needFitsSystemWindow = builder.needFitsSystemWindow;
    }

    public int getAgreementBaseTextColorId() {
        return this.agreementBaseTextColorId;
    }

    public int getAgreementCMHKText() {
        return this.agreementCMHKText;
    }

    public String getAgreementCMHKTextString() {
        return this.agreementCMHKTextString;
    }

    public int getAgreementCmccText() {
        return this.agreementCmccText;
    }

    public String getAgreementCmccTextString() {
        return this.agreementCmccTextString;
    }

    public int getAgreementColorId() {
        return this.agreementColorId;
    }

    public int getAgreementCtccText() {
        return this.agreementCtccText;
    }

    public String getAgreementCtccTextString() {
        return this.agreementCtccTextString;
    }

    public int getAgreementCuccText() {
        return this.agreementCuccText;
    }

    public String getAgreementCuccTextString() {
        return this.agreementCuccTextString;
    }

    public int getAgreementOffsetBottomY() {
        return this.agreementOffsetBottomY;
    }

    public int getAgreementOffsetRightX() {
        return this.agreementOffsetRightX;
    }

    public int getAgreementOffsetX() {
        return this.agreementOffsetX;
    }

    public int getAgreementOffsetY() {
        return this.agreementOffsetY;
    }

    public Drawable getAgreementPageCloseImg() {
        return this.agreementPageCloseImg;
    }

    public int getAgreementPageCloseImgHeight() {
        return this.agreementPageCloseImgHeight;
    }

    public int getAgreementPageCloseImgId() {
        return this.agreementPageCloseImgId;
    }

    public ImageView.ScaleType getAgreementPageCloseImgScaleType() {
        return this.agreementPageCloseImgScaleType;
    }

    public int getAgreementPageCloseImgWidth() {
        return this.agreementPageCloseImgWidth;
    }

    public int getAgreementPageTitle() {
        return this.agreementPageTitle;
    }

    public String getAgreementPageTitleString() {
        return this.agreementPageTitleString;
    }

    public int getAgreementPageTitleTextColor() {
        return this.agreementPageTitleTextColor;
    }

    public int getAgreementPageTitleTextSize() {
        return this.agreementPageTitleTextSize;
    }

    public SpannableString getAgreementText() {
        return this.agreementText;
    }

    public int getAgreementTextAnd1() {
        return this.agreementTextAnd1;
    }

    public int getAgreementTextAnd2() {
        return this.agreementTextAnd2;
    }

    public int getAgreementTextAnd3() {
        return this.agreementTextAnd3;
    }

    public String getAgreementTextAndString1() {
        return this.agreementTextAndString1;
    }

    public String getAgreementTextAndString2() {
        return this.agreementTextAndString2;
    }

    public String getAgreementTextAndString3() {
        return this.agreementTextAndString3;
    }

    public int getAgreementTextEnd() {
        return this.agreementTextEnd;
    }

    public String getAgreementTextEndString() {
        return this.agreementTextEndString;
    }

    public int getAgreementTextSize() {
        return this.agreementTextSize;
    }

    public int getAgreementTextStart() {
        return this.agreementTextStart;
    }

    public String getAgreementTextStartString() {
        return this.agreementTextStartString;
    }

    public String getAgreementUncheckHintText() {
        return this.agreementUncheckHintText;
    }

    public int getAgreementUncheckHintTextId() {
        return this.agreementUncheckHintTextId;
    }

    public int getAgreementUncheckHintType() {
        return this.agreementUncheckHintType;
    }

    public Toast getAgreementUncheckToast() {
        return this.agreementUncheckToast;
    }

    public Drawable getBackgroundImg() {
        return this.backgroundImg;
    }

    public int getBackgroundImgId() {
        return this.backgroundImgId;
    }

    public boolean getCheckboxDefaultState() {
        return this.checkboxDefaultState;
    }

    public Drawable getCheckboxImg() {
        return this.checkboxImg;
    }

    public int getCheckboxImgId() {
        return this.checkboxImgId;
    }

    public int getCheckboxOffsetBottomY() {
        return this.checkboxOffsetBottomY;
    }

    public int getCheckboxOffsetRightX() {
        return this.checkboxOffsetRightX;
    }

    public int getCheckboxOffsetX() {
        return this.checkboxOffsetX;
    }

    public int getCheckboxOffsetY() {
        return this.checkboxOffsetY;
    }

    public float getCheckboxScaleX() {
        return this.checkboxScaleX;
    }

    public float getCheckboxScaleY() {
        return this.checkboxScaleY;
    }

    public int getCusAgreementColor1() {
        return this.cusAgreementColor1;
    }

    public int getCusAgreementColor2() {
        return this.cusAgreementColor2;
    }

    public int getCusAgreementColor3() {
        return this.cusAgreementColor3;
    }

    public String getCusAgreementName1() {
        return this.cusAgreementName1;
    }

    public String getCusAgreementName2() {
        return this.cusAgreementName2;
    }

    public String getCusAgreementName3() {
        return this.cusAgreementName3;
    }

    public int getCusAgreementNameId1() {
        return this.cusAgreementNameId1;
    }

    public int getCusAgreementNameId2() {
        return this.cusAgreementNameId2;
    }

    public int getCusAgreementNameId3() {
        return this.cusAgreementNameId3;
    }

    public int getCusAgreementPageOneTitle() {
        return this.cusAgreementPageOneTitle;
    }

    public String getCusAgreementPageOneTitleString() {
        return this.cusAgreementPageOneTitleString;
    }

    public int getCusAgreementPageThreeTitle() {
        return this.cusAgreementPageThreeTitle;
    }

    public String getCusAgreementPageThreeTitleString() {
        return this.cusAgreementPageThreeTitleString;
    }

    public int getCusAgreementPageTwoTitle() {
        return this.cusAgreementPageTwoTitle;
    }

    public String getCusAgreementPageTwoTitleString() {
        return this.cusAgreementPageTwoTitleString;
    }

    public String getCusAgreementUrl1() {
        return this.cusAgreementUrl1;
    }

    public String getCusAgreementUrl2() {
        return this.cusAgreementUrl2;
    }

    public String getCusAgreementUrl3() {
        return this.cusAgreementUrl3;
    }

    public Drawable getDialogBackgroundDrawable() {
        return this.dialogBackgroundDrawable;
    }

    public int getDialogHeight() {
        return this.dialogHeight;
    }

    public int getDialogMaskBackground() {
        return this.dialogBackground;
    }

    public int getDialogOffsetX() {
        return this.dialogOffsetX;
    }

    public int getDialogOffsetY() {
        return this.dialogOffsetY;
    }

    public int getDialogWidth() {
        return this.dialogWidth;
    }

    public int getFinishInAnim() {
        return this.finishInAnim;
    }

    public int getFinishOutAnim() {
        return this.finishOutAnim;
    }

    public int getLoginBtnHeight() {
        return this.loginBtnHeight;
    }

    public Drawable getLoginBtnImg() {
        return this.loginBtnImg;
    }

    public int getLoginBtnImgId() {
        return this.loginBtnImgId;
    }

    public int getLoginBtnOffsetBottomY() {
        return this.loginBtnOffsetBottomY;
    }

    public int getLoginBtnOffsetRightX() {
        return this.loginBtnOffsetRightX;
    }

    public int getLoginBtnOffsetX() {
        return this.loginBtnOffsetX;
    }

    public int getLoginBtnOffsetY() {
        return this.loginBtnOffsetY;
    }

    public String getLoginBtnText() {
        return this.loginBtnText;
    }

    public int getLoginBtnTextColorId() {
        return this.loginBtnTextColorId;
    }

    public int getLoginBtnTextId() {
        return this.loginBtnTextId;
    }

    public int getLoginBtnTextSize() {
        return this.loginBtnTextSize;
    }

    public int getLoginBtnWidth() {
        return this.loginBtnWidth;
    }

    public int getLogoHeight() {
        return this.logoHeight;
    }

    public Drawable getLogoImg() {
        return this.logoImg;
    }

    public int getLogoImgId() {
        return this.logoImgId;
    }

    public int getLogoOffsetBottomY() {
        return this.logoOffsetBottomY;
    }

    public int getLogoOffsetRightX() {
        return this.logoOffsetRightX;
    }

    public int getLogoOffsetX() {
        return this.logoOffsetX;
    }

    public int getLogoOffsetY() {
        return this.logoOffsetY;
    }

    public int getLogoWidth() {
        return this.logoWidth;
    }

    public Drawable getNavCloseImg() {
        return this.navCloseImg;
    }

    public int getNavCloseImgHeight() {
        return this.navCloseImgHeight;
    }

    public int getNavCloseImgId() {
        return this.navCloseImgId;
    }

    public int getNavCloseImgOffsetRightX() {
        return this.navCloseImgOffsetRightX;
    }

    public int getNavCloseImgOffsetX() {
        return this.navCloseImgOffsetX;
    }

    public int getNavCloseImgOffsetY() {
        return this.navCloseImgOffsetY;
    }

    public ImageView.ScaleType getNavCloseImgScaleType() {
        return this.navCloseImgScaleType;
    }

    public int getNavCloseImgWidth() {
        return this.navCloseImgWidth;
    }

    public int getNavColorId() {
        return this.navColorId;
    }

    public String getNavText() {
        return this.navText;
    }

    public int getNavTextColorId() {
        return this.navTextColorId;
    }

    public int getNavTextId() {
        return this.navTextId;
    }

    public int getNavTextSize() {
        return this.navTextSize;
    }

    public Boolean getNeedFitsSystemWindow() {
        return this.needFitsSystemWindow;
    }

    public int getNumberColorId() {
        return this.numberColorId;
    }

    public int getNumberOffsetBottomY() {
        return this.numberOffsetBottomY;
    }

    public int getNumberOffsetRightX() {
        return this.numberOffsetRightX;
    }

    public int getNumberOffsetX() {
        return this.numberOffsetX;
    }

    public int getNumberOffsetY() {
        return this.numberOffsetY;
    }

    public int getNumberSizeId() {
        return this.numberSizeId;
    }

    public int getSloganOffsetBottomY() {
        return this.sloganOffsetBottomY;
    }

    public int getSloganOffsetRightX() {
        return this.sloganOffsetRightX;
    }

    public int getSloganOffsetX() {
        return this.sloganOffsetX;
    }

    public int getSloganOffsetY() {
        return this.sloganOffsetY;
    }

    public int getSloganTextColor() {
        return this.sloganTextColor;
    }

    public int getSloganTextSize() {
        return this.sloganTextSize;
    }

    public int getStartInAnim() {
        return this.startInAnim;
    }

    public int getStartOutAnim() {
        return this.startOutAnim;
    }

    public int getSwitchAccColorId() {
        return this.switchAccColorId;
    }

    public int getSwitchAccOffsetBottomY() {
        return this.switchAccOffsetBottomY;
    }

    public int getSwitchAccOffsetRightX() {
        return this.switchAccOffsetRightX;
    }

    public int getSwitchAccOffsetX() {
        return this.switchAccOffsetX;
    }

    public int getSwitchAccOffsetY() {
        return this.switchAccOffsetY;
    }

    public int getSwitchAccText() {
        return this.switchAccText;
    }

    public int getSwitchAccTextSize() {
        return this.switchAccTextSize;
    }

    public String getSwitchAccTextString() {
        return this.switchAccTextString;
    }

    public boolean hasFinishActivityAnim() {
        return this.hasFinishActivityAnim;
    }

    public boolean hasStartActivityAnim() {
        return this.hasStartActivityAnim;
    }

    public boolean isAgreementAlignParentRight() {
        return this.agreementAlignParentRight;
    }

    public boolean isAgreementGravityLeft() {
        return this.agreementGravityLeft;
    }

    public boolean isAgreementHidden() {
        return this.agreementHidden;
    }

    public boolean isAgreementPageCloseImgHidden() {
        return this.agreementPageCloseImgHidden;
    }

    public boolean isAgreementPageTitleHidden() {
        return this.agreementPageTitleHidden;
    }

    public boolean isAgreementPageTitleTextBold() {
        return this.agreementPageTitleTextBold;
    }

    public boolean isAgreementTextBold() {
        return this.agreementTextBold;
    }

    public boolean isAgreementTextWithUnderLine() {
        return this.agreementTextWithUnderLine;
    }

    public boolean isBackgroundClickClose() {
        return this.backgroundClickClose;
    }

    public boolean isBottomTranslateAnim() {
        return this.bottomTranslateAnim;
    }

    public boolean isCheckboxDefaultState() {
        return this.checkboxDefaultState;
    }

    public boolean isCheckboxHidden() {
        return this.checkboxHidden;
    }

    public boolean isCusAgreementColor1Seted() {
        return this.cusAgreementColor1Seted;
    }

    public boolean isCusAgreementColor2Seted() {
        return this.cusAgreementColor2Seted;
    }

    public boolean isCusAgreementColor3Seted() {
        return this.cusAgreementColor3Seted;
    }

    public boolean isDialogAlignBottom() {
        return this.dialogAlignBottom;
    }

    public boolean isDialogBackgroundClickClose() {
        return this.dialogBackgroundClickClose;
    }

    public boolean isDialogTheme() {
        return this.dialogTheme;
    }

    public boolean isFadeAnim() {
        return this.fadeAnim;
    }

    public boolean isFullScreen() {
        return this.fullScreen;
    }

    public boolean isImmersiveStatusTextColorBlack() {
        return this.immersiveStatusTextColorBlack;
    }

    public boolean isImmersiveTheme() {
        return this.immersiveTheme;
    }

    public boolean isLoginBtnAlignParentRight() {
        return this.loginBtnAlignParentRight;
    }

    public boolean isLoginBtnHidden() {
        return this.loginBtnHidden;
    }

    public boolean isLoginBtnTextBold() {
        return this.loginBtnTextBold;
    }

    public boolean isLogoAlignParentRight() {
        return this.logoAlignParentRight;
    }

    public boolean isLogoHidden() {
        return this.logoHidden;
    }

    public boolean isNavCloseImgHidden() {
        return this.navCloseImgHidden;
    }

    public boolean isNavHidden() {
        return this.navHidden;
    }

    public boolean isNavTextBold() {
        return this.navTextBold;
    }

    public boolean isNavTransparent() {
        return this.navTransparent;
    }

    public boolean isNumberAlignParentRight() {
        return this.numberAlignParentRight;
    }

    public boolean isNumberBold() {
        return this.numberBold;
    }

    public boolean isNumberHidden() {
        return this.numberHidden;
    }

    public boolean isRightTranslateAnim() {
        return this.rightTranslateAnim;
    }

    public boolean isSloganAlignParentRight() {
        return this.sloganAlignParentRight;
    }

    public boolean isSloganHidden() {
        return this.sloganHidden;
    }

    public boolean isSloganTextBold() {
        return this.sloganTextBold;
    }

    public boolean isSwitchAccAlignParentRight() {
        return this.switchAccAlignParentRight;
    }

    public boolean isSwitchAccHidden() {
        return this.switchAccHidden;
    }

    public boolean isSwitchAccTextBold() {
        return this.switchAccTextBold;
    }

    public boolean isTranslateAnim() {
        return this.translateAnim;
    }

    public boolean isVirtualButtonTransparent() {
        return this.virtualButtonTransparent;
    }

    public boolean isZoomAnim() {
        return this.zoomAnim;
    }

    public void setNeedFitsSystemWindow(Boolean bool) {
        this.needFitsSystemWindow = bool;
    }
}