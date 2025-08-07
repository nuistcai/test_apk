package cn.fly.verify;

import cn.fly.tools.proguard.PublicMemberKeeper;

/* loaded from: classes.dex */
public enum m implements PublicMemberKeeper {
    C_UNSUPPORTED_OPERATOR(6119000, "Unsupported operator"),
    C_PHONE_INVALID(6119001, "Invalid phone"),
    C_NO_SIM(6119002, "No sim"),
    C_LACK_OF_PERMISSIONS(6119003, "Lack of necessary permissions"),
    C_CELLULAR_DISABLED(6119004, "Cellular disabled"),
    C_ONE_KEY_OBTAIN_OPERATOR_CONFIG_ERR(6119401, "Obtain operator config error"),
    C_ONE_KEY_LOGIN_ERR(6119402, "login error"),
    C_ONE_KEY_OBTAIN_OPERATOR_ACCESS_CODE_ERR(6119403, "Obtain access code from operator error"),
    C_ONE_KEY_OBTAIN_OPERATOR_ACCESS_TOKEN_ERR(6119404, "Obtain access token from operator error"),
    C_ONE_KEY_OBTAIN_CM_OPERATOR_ACCESS_CODE_ERR(6119127, "Obtain access code from cm operator error"),
    C_ONE_KEY_OBTAIN_CU_OPERATOR_ACCESS_CODE_ERR(6119128, "Obtain access code from cu operator error"),
    C_ONE_KEY_OBTAIN_XW_OPERATOR_ACCESS_CODE_ERR(6119130, "Obtain access code from xw operator error"),
    C_ONE_KEY_OBTAIN_XW_OPERATOR_ACCESS_TOKEN_ERR(6119166, "Obtain access token from xw operator error"),
    C_ONE_KEY_OBTAIN_CT_OPERATOR_ACCESS_CODE_ERR(6119129, "Obtain access code from ct operator error"),
    C_ONE_KEY_OBTAIN_CM_OPERATOR_ACCESS_TOKEN_ERR(6119167, "Obtain access token from cm operator error"),
    C_ONE_KEY_OBTAIN_CU_OPERATOR_ACCESS_TOKEN_ERR(6119168, "Obtain access token from cu operator error"),
    C_ONE_KEY_OBTAIN_CT_OPERATOR_ACCESS_TOKEN_ERR(6119169, "Obtain access token from ct operator error"),
    C_ONE_KEY_OBTAIN_SERVER_TOKEN_ERR(6119405, "Obtain server token error"),
    C_MISSING_REQUIRED_PARAMS(6119095, "Missing required parameters"),
    C_ILLEGAL_PARAMS(6119096, "Illegal parameters"),
    C_RESPONSE_DATA_ABNORMAL(6119097, "Response data from server abnormal"),
    C_NETWORK_ERROR(6119098, "Network error"),
    C_UNKNOWN_ERROR(6119099, "Unknown error"),
    C_INIT_CARRIER_ERROR(6119105, "init carrier error"),
    C_NOUI_SWITCH_OFF(6119107, "No UI Switch off"),
    C_PREVERIFY_CATCH(6119123, "preVerify exception"),
    C_PREVERIFY_TIMEOUT(6119124, "preVerify timeout"),
    C_VERIFY_CATCH(6119165, "verify exception"),
    C_CONFIG_ERROR(6119170, "No operator configuration"),
    C_DECODE_ERROR(6119171, "Decode configuration error"),
    C_INIT_SERVER_ERROR(6119101, "Init failed"),
    C_INIT_NO_NET(6119102, "Network error"),
    C_PRIVACY_NOT_ACCEPTED_ERROR(6119103, "privacy Not accepted error"),
    C_INIT_TIMEOUT(6119104, "Init timeout"),
    C_INIT_UNEXPECTED_ERROR(6119105, "Init exception"),
    C_APPKEY_NULL(6119106, "AppKey is null"),
    C_CACHE_FAILED(6119107, "cache failed"),
    C_AUTHPAGE_NO_PRIVACY(6119143, "privacy Not accepted"),
    C_AUTHPAGE_TIMEOUT(6119144, "Authpage timeout"),
    C_AUTHPAGE_NO_NET(6119142, "Network error"),
    C_APPID_NULL(6119108, "AppId or Secret is null"),
    C_CANCEL_LOGIN(6119150, "click_return_button"),
    C_OTHER_LOGIN(6119152, "other way login"),
    C_AUTPAGE_OPENED(6119154, "oauthpage_opened"),
    INNER_NO_INIT_NO_RETRY(91700, "no init no retry"),
    INNER_NO_INIT_RETRY(91701, "no init retry"),
    INNER_UNKNOWN_OPERATOR(90000, "unknown operator"),
    INNER_NO_OPERATOR_CONFIG(90001, "no operator config"),
    INNER_UNKNOWN_OPERATOR_TRIED(90002, "unknown operator tried"),
    INNER_TIMEOUT_ERR(91200, "timeout"),
    INNER_NO_CELL_ERR(91201, "no cell"),
    INNER_NO_CELL_WIFI_ERR(91202, "no cell wifi"),
    INNER_NO_CELL_WIFI_UNKNOWN_ERR(91203, "no cell wifi unknown"),
    INNER_NO_SWITCH_PERMISSION_ERR(91204, "no switch permission"),
    INNER_SWITCH_EXCEPTION_ERR(91205, "switch exception"),
    INNER_OTHER_EXCEPTION_ERR(91206, "other exception"),
    INNER_TOKEN_NULL_ERR(91207, "token null"),
    INNER_VERIFY_TIMEOUT_ERR(91208, "timeout"),
    INNER_CANCEL_LOGIN(91500, "User cancel grant"),
    INNER_OTHER_LOGIN(91501, "User request other login");

    private int ai;
    private String aj;

    m(int i, String str) {
        this.ai = i;
        this.aj = cn.fly.verify.util.l.a(i, str);
    }

    public int a() {
        return this.ai;
    }

    public String b() {
        return this.aj;
    }
}