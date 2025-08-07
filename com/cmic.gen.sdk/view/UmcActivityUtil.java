package com.cmic.gen.sdk.view;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cmic.gen.sdk.auth.GenAuthnHelper;
import com.cmic.gen.sdk.e.LogUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* compiled from: UmcActivityUtil.java */
/* renamed from: com.cmic.gen.sdk.view.e, reason: use source file name */
/* loaded from: classes.dex */
public class UmcActivityUtil {
    private static ArrayList<WeakReference<Activity>> a = null;

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(GenAuthThemeConfig genAuthThemeConfig, String str, String str2) {
        if (genAuthThemeConfig.getClauseClickListener() != null) {
            genAuthThemeConfig.getClauseClickListener().onClauseClick(str, str2);
        }
    }

    public static SpannableString a(final Context context, String str, final String str2, final String str3, final ServerClauseDialog serverClauseDialog, final ArrayList<ServerClauseDialog> arrayList, final ArrayList<String> arrayList2) {
        ClickableSpan clickableSpan;
        ClickableSpan clickableSpan2;
        ClickableSpan clickableSpan3;
        int iIndexOf;
        LogUtils.b("getClauseSpannableString", str);
        SpannableString spannableString = new SpannableString(str);
        try {
            final GenAuthThemeConfig authThemeConfig = GenAuthnHelper.getInstance(context).getAuthThemeConfig();
            ClickableSpan clickableSpan4 = new ClickableSpan() { // from class: com.cmic.gen.sdk.view.e.1
                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                    try {
                        ds.setColor(GenAuthnHelper.getInstance(context).getAuthThemeConfig().getClauseColor());
                    } catch (Exception e) {
                        ds.setColor(-16007674);
                    }
                }

                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    UmcActivityUtil.b(authThemeConfig, str2, str3);
                    if (serverClauseDialog != null && !serverClauseDialog.isShowing()) {
                        serverClauseDialog.show();
                    }
                }
            };
            if (arrayList.size() >= 1) {
                clickableSpan = new ClickableSpan() { // from class: com.cmic.gen.sdk.view.e.2
                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                        try {
                            ds.setColor(GenAuthnHelper.getInstance(context).getAuthThemeConfig().getClauseColor());
                        } catch (Exception e) {
                            ds.setColor(-16007674);
                        }
                    }

                    @Override // android.text.style.ClickableSpan
                    public void onClick(View view) {
                        UmcActivityUtil.b(authThemeConfig, (String) arrayList2.get(0), authThemeConfig.getClauseUrl());
                        if (arrayList.get(0) != null && !((ServerClauseDialog) arrayList.get(0)).isShowing()) {
                            ((ServerClauseDialog) arrayList.get(0)).show();
                        }
                    }
                };
            } else {
                clickableSpan = null;
            }
            if (arrayList.size() >= 2) {
                clickableSpan2 = new ClickableSpan() { // from class: com.cmic.gen.sdk.view.e.3
                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                        try {
                            ds.setColor(GenAuthnHelper.getInstance(context).getAuthThemeConfig().getClauseColor());
                        } catch (Exception e) {
                            ds.setColor(-16007674);
                        }
                    }

                    @Override // android.text.style.ClickableSpan
                    public void onClick(View view) {
                        UmcActivityUtil.b(authThemeConfig, (String) arrayList2.get(1), authThemeConfig.getClauseUrl2());
                        if (arrayList.get(1) != null && !((ServerClauseDialog) arrayList.get(1)).isShowing()) {
                            ((ServerClauseDialog) arrayList.get(1)).show();
                        }
                    }
                };
            } else {
                clickableSpan2 = null;
            }
            if (arrayList.size() >= 3) {
                clickableSpan3 = new ClickableSpan() { // from class: com.cmic.gen.sdk.view.e.4
                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                        try {
                            ds.setColor(GenAuthnHelper.getInstance(context).getAuthThemeConfig().getClauseColor());
                        } catch (Exception e) {
                            ds.setColor(-16007674);
                        }
                    }

                    @Override // android.text.style.ClickableSpan
                    public void onClick(View view) {
                        UmcActivityUtil.b(authThemeConfig, (String) arrayList2.get(2), authThemeConfig.getClauseUrl3());
                        if (arrayList.get(2) != null && !((ServerClauseDialog) arrayList.get(2)).isShowing()) {
                            ((ServerClauseDialog) arrayList.get(2)).show();
                        }
                    }
                };
            } else {
                clickableSpan3 = null;
            }
            ClickableSpan clickableSpan5 = arrayList.size() == 4 ? new ClickableSpan() { // from class: com.cmic.gen.sdk.view.e.5
                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                    try {
                        ds.setColor(GenAuthnHelper.getInstance(context).getAuthThemeConfig().getClauseColor());
                    } catch (Exception e) {
                        ds.setColor(-16007674);
                    }
                }

                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    UmcActivityUtil.b(authThemeConfig, (String) arrayList2.get(3), authThemeConfig.getClauseUrl4());
                    if (arrayList.get(3) != null && !((ServerClauseDialog) arrayList.get(3)).isShowing()) {
                        ((ServerClauseDialog) arrayList.get(3)).show();
                    }
                }
            } : null;
            GenAuthnHelper.getInstance(context).getAuthThemeConfig();
            int iIndexOf2 = str.indexOf(str2);
            spannableString.setSpan(clickableSpan4, iIndexOf2, str2.length() + iIndexOf2, 34);
            if (arrayList.size() < 1) {
                iIndexOf = 0;
            } else {
                String str4 = arrayList2.get(0);
                iIndexOf = str.indexOf(str4);
                spannableString.setSpan(clickableSpan, iIndexOf, str4.length() + iIndexOf, 34);
            }
            if (arrayList.size() >= 2) {
                int length = iIndexOf + arrayList2.get(0).length();
                String str5 = arrayList2.get(1);
                iIndexOf = str.indexOf(str5, length);
                spannableString.setSpan(clickableSpan2, iIndexOf, str5.length() + iIndexOf, 34);
            }
            if (arrayList.size() >= 3) {
                int length2 = arrayList2.get(1).length() + iIndexOf;
                String str6 = arrayList2.get(2);
                int iIndexOf3 = str.indexOf(str6, length2);
                spannableString.setSpan(clickableSpan3, iIndexOf3, str6.length() + iIndexOf3, 34);
            }
            if (arrayList.size() == 4) {
                int length3 = iIndexOf + arrayList2.get(2).length();
                String str7 = arrayList2.get(3);
                int iIndexOf4 = str.indexOf(str7, length3);
                spannableString.setSpan(clickableSpan5, iIndexOf4, str7.length() + iIndexOf4, 34);
            }
            return spannableString;
        } catch (Exception e) {
            e.printStackTrace();
            return spannableString;
        }
    }

    public static RelativeLayout a(Context context, View view, int i, int i2, String str, View.OnClickListener onClickListener) {
        GenAuthThemeConfig authThemeConfig = GenAuthnHelper.getInstance(context).getAuthThemeConfig();
        RelativeLayout relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, view != null ? -2 : a(context, 49.0f));
        layoutParams.addRule(10, -1);
        relativeLayout.setLayoutParams(layoutParams);
        relativeLayout.setId(i);
        TextView textView = new TextView(context);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(13, -1);
        textView.setLayoutParams(layoutParams2);
        textView.setTextColor(authThemeConfig.getNavTextColor());
        textView.setTextSize(2, authThemeConfig.getNavTextSize());
        textView.setText(str);
        textView.setId(3355443);
        if (view != null) {
            relativeLayout.addView(view);
            if (view instanceof ViewGroup) {
                ((ViewGroup) view).addView(textView);
            } else {
                relativeLayout.addView(textView);
            }
            return relativeLayout;
        }
        relativeLayout.addView(textView);
        ImageButton imageButton = new ImageButton(context);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(a(context, authThemeConfig.getNavReturnImgWidth()), a(context, authThemeConfig.getNavReturnImgHeight()));
        layoutParams3.addRule(9, -1);
        layoutParams3.addRule(15, -1);
        layoutParams3.setMargins(a(context, 12.0f), 0, 0, 0);
        imageButton.setLayoutParams(layoutParams3);
        imageButton.setId(i2);
        imageButton.setOnClickListener(onClickListener);
        imageButton.setBackgroundColor(0);
        relativeLayout.addView(imageButton);
        try {
            relativeLayout.setBackgroundColor(GenAuthnHelper.getInstance(context).getAuthThemeConfig().getNavColor());
        } catch (Exception e) {
            relativeLayout.setBackgroundColor(-16742704);
        }
        imageButton.setImageResource(ResourceUtil.b(context, "umcsdk_return_bg"));
        return relativeLayout;
    }

    public static int a(Context context, float f) {
        if (f < 0.0f) {
            return (int) f;
        }
        try {
            return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
        } catch (Exception e) {
            return (int) f;
        }
    }

    public static int a(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics.widthPixels;
    }

    public static int b(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics.heightPixels;
    }
}