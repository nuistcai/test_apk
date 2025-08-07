package com.squareup.leakcanary.internal;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.squareup.leakcanary.Exclusion;
import com.squareup.leakcanary.LeakTrace;
import com.squareup.leakcanary.LeakTraceElement;
import com.squareup.leakcanary.R;
import com.squareup.leakcanary.internal.DisplayLeakConnectorView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
final class DisplayLeakAdapter extends BaseAdapter {
    private static final int NORMAL_ROW = 1;
    private static final int TOP_ROW = 0;
    private String referenceKey;
    private boolean[] opened = new boolean[0];
    private List<LeakTraceElement> elements = Collections.emptyList();
    private String referenceName = "";

    DisplayLeakAdapter() {
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if (getItemViewType(position) == 0) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.leak_canary_ref_top_row, parent, false);
            }
            TextView textView = (TextView) findById(convertView, R.id.leak_canary_row_text);
            textView.setText(context.getPackageName());
        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.leak_canary_ref_row, parent, false);
            }
            TextView textView2 = (TextView) findById(convertView, R.id.leak_canary_row_text);
            boolean isRoot = position == 1;
            boolean isLeakingInstance = position == getCount() - 1;
            LeakTraceElement element = getItem(position);
            String htmlString = elementToHtmlString(element, isRoot, this.opened[position]);
            if (isLeakingInstance && !this.referenceName.equals("") && this.opened[position]) {
                htmlString = htmlString + " <font color='#919191'>" + this.referenceName + "</font>";
            }
            textView2.setText(Html.fromHtml(htmlString));
            DisplayLeakConnectorView connector = (DisplayLeakConnectorView) findById(convertView, R.id.leak_canary_row_connector);
            if (isRoot) {
                connector.setType(DisplayLeakConnectorView.Type.START);
            } else if (isLeakingInstance) {
                connector.setType(DisplayLeakConnectorView.Type.END);
            } else {
                connector.setType(DisplayLeakConnectorView.Type.NODE);
            }
            MoreDetailsView moreDetailsView = (MoreDetailsView) findById(convertView, R.id.leak_canary_row_more);
            moreDetailsView.setOpened(this.opened[position]);
        }
        return convertView;
    }

    private String elementToHtmlString(LeakTraceElement element, boolean root, boolean opened) {
        String qualifier;
        String simpleName;
        String htmlString;
        String htmlString2 = "";
        if (element.referenceName == null) {
            htmlString2 = "leaks ";
        } else if (!root) {
            htmlString2 = "references ";
        }
        if (element.type == LeakTraceElement.Type.STATIC_FIELD) {
            htmlString2 = htmlString2 + "<font color='#c48a47'>static</font> ";
        }
        if (element.holder == LeakTraceElement.Holder.ARRAY || element.holder == LeakTraceElement.Holder.THREAD) {
            htmlString2 = htmlString2 + "<font color='#f3cf83'>" + element.holder.name().toLowerCase() + "</font> ";
        }
        int separator = element.className.lastIndexOf(46);
        if (separator == -1) {
            qualifier = "";
            simpleName = element.className;
        } else {
            String qualifier2 = element.className;
            qualifier = qualifier2.substring(0, separator + 1);
            simpleName = element.className.substring(separator + 1);
        }
        if (opened) {
            htmlString2 = htmlString2 + "<font color='#919191'>" + qualifier + "</font>";
        }
        String styledClassName = "<font color='#ffffff'>" + simpleName + "</font>";
        String htmlString3 = htmlString2 + styledClassName;
        if (element.referenceName != null) {
            htmlString = htmlString3 + ".<font color='#998bb5'>" + element.referenceName.replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "</font>";
        } else {
            htmlString = htmlString3 + " <font color='#f3cf83'>instance</font>";
        }
        if (opened && element.extra != null) {
            htmlString = htmlString + " <font color='#919191'>" + element.extra + "</font>";
        }
        Exclusion exclusion = element.exclusion;
        if (exclusion != null) {
            if (opened) {
                String htmlString4 = htmlString + "<br/><br/>Excluded by rule";
                if (exclusion.name != null) {
                    htmlString4 = htmlString4 + " <font color='#ffffff'>" + exclusion.name + "</font>";
                }
                String htmlString5 = htmlString4 + " matching <font color='#f3cf83'>" + exclusion.matching + "</font>";
                if (exclusion.reason != null) {
                    return htmlString5 + " because <font color='#f3cf83'>" + exclusion.reason + "</font>";
                }
                return htmlString5;
            }
            return htmlString + " (excluded)";
        }
        return htmlString;
    }

    public void update(LeakTrace leakTrace, String referenceKey, String referenceName) {
        if (referenceKey.equals(this.referenceKey)) {
            return;
        }
        this.referenceKey = referenceKey;
        this.referenceName = referenceName;
        this.elements = new ArrayList(leakTrace.elements);
        this.opened = new boolean[this.elements.size() + 1];
        notifyDataSetChanged();
    }

    public void toggleRow(int position) {
        this.opened[position] = !this.opened[position];
        notifyDataSetChanged();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.elements.size() + 1;
    }

    @Override // android.widget.Adapter
    public LeakTraceElement getItem(int position) {
        if (getItemViewType(position) == 0) {
            return null;
        }
        return this.elements.get(position - 1);
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        return 2;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        return 1;
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    private static <T extends View> T findById(View view, int i) {
        return (T) view.findViewById(i);
    }
}