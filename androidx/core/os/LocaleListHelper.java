package androidx.core.os;

import android.os.Build;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import kotlin.jvm.internal.IntCompanionObject;

/* loaded from: classes.dex */
final class LocaleListHelper {
    private static final int NUM_PSEUDO_LOCALES = 2;
    private static final String STRING_AR_XB = "ar-XB";
    private static final String STRING_EN_XA = "en-XA";
    private final Locale[] mList;
    private final String mStringRepresentation;
    private static final Locale[] sEmptyList = new Locale[0];
    private static final LocaleListHelper sEmptyLocaleList = new LocaleListHelper(new Locale[0]);
    private static final Locale LOCALE_EN_XA = new Locale("en", "XA");
    private static final Locale LOCALE_AR_XB = new Locale("ar", "XB");
    private static final Locale EN_LATN = LocaleHelper.forLanguageTag("en-Latn");
    private static final Object sLock = new Object();
    private static LocaleListHelper sLastExplicitlySetLocaleList = null;
    private static LocaleListHelper sDefaultLocaleList = null;
    private static LocaleListHelper sDefaultAdjustedLocaleList = null;
    private static Locale sLastDefaultLocale = null;

    Locale get(int index) {
        if (index < 0 || index >= this.mList.length) {
            return null;
        }
        return this.mList[index];
    }

    boolean isEmpty() {
        return this.mList.length == 0;
    }

    int size() {
        return this.mList.length;
    }

    int indexOf(Locale locale) {
        for (int i = 0; i < this.mList.length; i++) {
            if (this.mList[i].equals(locale)) {
                return i;
            }
        }
        return -1;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof LocaleListHelper)) {
            return false;
        }
        Locale[] otherList = ((LocaleListHelper) other).mList;
        if (this.mList.length != otherList.length) {
            return false;
        }
        for (int i = 0; i < this.mList.length; i++) {
            if (!this.mList[i].equals(otherList[i])) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int result = 1;
        for (int i = 0; i < this.mList.length; i++) {
            result = (result * 31) + this.mList[i].hashCode();
        }
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < this.mList.length; i++) {
            sb.append(this.mList[i]);
            if (i < this.mList.length - 1) {
                sb.append(',');
            }
        }
        sb.append("]");
        return sb.toString();
    }

    String toLanguageTags() {
        return this.mStringRepresentation;
    }

    LocaleListHelper(Locale... list) {
        if (list.length == 0) {
            this.mList = sEmptyList;
            this.mStringRepresentation = "";
            return;
        }
        Locale[] localeList = new Locale[list.length];
        HashSet<Locale> seenLocales = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.length; i++) {
            Locale l = list[i];
            if (l == null) {
                throw new NullPointerException("list[" + i + "] is null");
            }
            if (seenLocales.contains(l)) {
                throw new IllegalArgumentException("list[" + i + "] is a repetition");
            }
            Locale localeClone = (Locale) l.clone();
            localeList[i] = localeClone;
            sb.append(LocaleHelper.toLanguageTag(localeClone));
            if (i < list.length - 1) {
                sb.append(',');
            }
            seenLocales.add(localeClone);
        }
        this.mList = localeList;
        this.mStringRepresentation = sb.toString();
    }

    LocaleListHelper(Locale topLocale, LocaleListHelper otherLocales) {
        if (topLocale == null) {
            throw new NullPointerException("topLocale is null");
        }
        int inputLength = otherLocales == null ? 0 : otherLocales.mList.length;
        int topLocaleIndex = -1;
        int i = 0;
        while (true) {
            if (i >= inputLength) {
                break;
            }
            if (!topLocale.equals(otherLocales.mList[i])) {
                i++;
            } else {
                topLocaleIndex = i;
                break;
            }
        }
        int outputLength = (topLocaleIndex == -1 ? 1 : 0) + inputLength;
        Locale[] localeList = new Locale[outputLength];
        localeList[0] = (Locale) topLocale.clone();
        if (topLocaleIndex == -1) {
            for (int i2 = 0; i2 < inputLength; i2++) {
                localeList[i2 + 1] = (Locale) otherLocales.mList[i2].clone();
            }
        } else {
            for (int i3 = 0; i3 < topLocaleIndex; i3++) {
                localeList[i3 + 1] = (Locale) otherLocales.mList[i3].clone();
            }
            for (int i4 = topLocaleIndex + 1; i4 < inputLength; i4++) {
                localeList[i4] = (Locale) otherLocales.mList[i4].clone();
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i5 = 0; i5 < outputLength; i5++) {
            sb.append(LocaleHelper.toLanguageTag(localeList[i5]));
            if (i5 < outputLength - 1) {
                sb.append(',');
            }
        }
        this.mList = localeList;
        this.mStringRepresentation = sb.toString();
    }

    static LocaleListHelper getEmptyLocaleList() {
        return sEmptyLocaleList;
    }

    static LocaleListHelper forLanguageTags(String list) {
        if (list == null || list.isEmpty()) {
            return getEmptyLocaleList();
        }
        String[] tags = list.split(",", -1);
        Locale[] localeArray = new Locale[tags.length];
        for (int i = 0; i < localeArray.length; i++) {
            localeArray[i] = LocaleHelper.forLanguageTag(tags[i]);
        }
        return new LocaleListHelper(localeArray);
    }

    private static String getLikelyScript(Locale locale) {
        if (Build.VERSION.SDK_INT < 21) {
            return "";
        }
        String script = locale.getScript();
        return !script.isEmpty() ? script : "";
    }

    private static boolean isPseudoLocale(String locale) {
        return STRING_EN_XA.equals(locale) || STRING_AR_XB.equals(locale);
    }

    private static boolean isPseudoLocale(Locale locale) {
        return LOCALE_EN_XA.equals(locale) || LOCALE_AR_XB.equals(locale);
    }

    private static int matchScore(Locale locale, Locale locale2) {
        if (locale.equals(locale2)) {
            return 1;
        }
        if (!locale.getLanguage().equals(locale2.getLanguage()) || isPseudoLocale(locale) || isPseudoLocale(locale2)) {
            return 0;
        }
        String likelyScript = getLikelyScript(locale);
        if (!likelyScript.isEmpty()) {
            return likelyScript.equals(getLikelyScript(locale2)) ? 1 : 0;
        }
        String country = locale.getCountry();
        return (country.isEmpty() || country.equals(locale2.getCountry())) ? 1 : 0;
    }

    private int findFirstMatchIndex(Locale supportedLocale) {
        for (int idx = 0; idx < this.mList.length; idx++) {
            int score = matchScore(supportedLocale, this.mList[idx]);
            if (score > 0) {
                return idx;
            }
        }
        return IntCompanionObject.MAX_VALUE;
    }

    private int computeFirstMatchIndex(Collection<String> supportedLocales, boolean assumeEnglishIsSupported) {
        if (this.mList.length == 1) {
            return 0;
        }
        if (this.mList.length == 0) {
            return -1;
        }
        int bestIndex = IntCompanionObject.MAX_VALUE;
        if (assumeEnglishIsSupported) {
            int idx = findFirstMatchIndex(EN_LATN);
            if (idx == 0) {
                return 0;
            }
            if (idx < Integer.MAX_VALUE) {
                bestIndex = idx;
            }
        }
        for (String languageTag : supportedLocales) {
            Locale supportedLocale = LocaleHelper.forLanguageTag(languageTag);
            int idx2 = findFirstMatchIndex(supportedLocale);
            if (idx2 == 0) {
                return 0;
            }
            if (idx2 < bestIndex) {
                bestIndex = idx2;
            }
        }
        if (bestIndex == Integer.MAX_VALUE) {
            return 0;
        }
        return bestIndex;
    }

    private Locale computeFirstMatch(Collection<String> supportedLocales, boolean assumeEnglishIsSupported) {
        int bestIndex = computeFirstMatchIndex(supportedLocales, assumeEnglishIsSupported);
        if (bestIndex == -1) {
            return null;
        }
        return this.mList[bestIndex];
    }

    Locale getFirstMatch(String[] supportedLocales) {
        return computeFirstMatch(Arrays.asList(supportedLocales), false);
    }

    int getFirstMatchIndex(String[] supportedLocales) {
        return computeFirstMatchIndex(Arrays.asList(supportedLocales), false);
    }

    Locale getFirstMatchWithEnglishSupported(String[] supportedLocales) {
        return computeFirstMatch(Arrays.asList(supportedLocales), true);
    }

    int getFirstMatchIndexWithEnglishSupported(Collection<String> supportedLocales) {
        return computeFirstMatchIndex(supportedLocales, true);
    }

    int getFirstMatchIndexWithEnglishSupported(String[] supportedLocales) {
        return getFirstMatchIndexWithEnglishSupported(Arrays.asList(supportedLocales));
    }

    static boolean isPseudoLocalesOnly(String[] supportedLocales) {
        if (supportedLocales == null) {
            return true;
        }
        if (supportedLocales.length > 3) {
            return false;
        }
        for (String locale : supportedLocales) {
            if (!locale.isEmpty() && !isPseudoLocale(locale)) {
                return false;
            }
        }
        return true;
    }

    static LocaleListHelper getDefault() {
        Locale defaultLocale = Locale.getDefault();
        synchronized (sLock) {
            if (!defaultLocale.equals(sLastDefaultLocale)) {
                sLastDefaultLocale = defaultLocale;
                if (sDefaultLocaleList != null && defaultLocale.equals(sDefaultLocaleList.get(0))) {
                    return sDefaultLocaleList;
                }
                sDefaultLocaleList = new LocaleListHelper(defaultLocale, sLastExplicitlySetLocaleList);
                sDefaultAdjustedLocaleList = sDefaultLocaleList;
            }
            return sDefaultLocaleList;
        }
    }

    static LocaleListHelper getAdjustedDefault() {
        LocaleListHelper localeListHelper;
        getDefault();
        synchronized (sLock) {
            localeListHelper = sDefaultAdjustedLocaleList;
        }
        return localeListHelper;
    }

    static void setDefault(LocaleListHelper locales) {
        setDefault(locales, 0);
    }

    static void setDefault(LocaleListHelper locales, int localeIndex) {
        if (locales == null) {
            throw new NullPointerException("locales is null");
        }
        if (locales.isEmpty()) {
            throw new IllegalArgumentException("locales is empty");
        }
        synchronized (sLock) {
            sLastDefaultLocale = locales.get(localeIndex);
            Locale.setDefault(sLastDefaultLocale);
            sLastExplicitlySetLocaleList = locales;
            sDefaultLocaleList = locales;
            if (localeIndex == 0) {
                sDefaultAdjustedLocaleList = sDefaultLocaleList;
            } else {
                sDefaultAdjustedLocaleList = new LocaleListHelper(sLastDefaultLocale, sDefaultLocaleList);
            }
        }
    }
}