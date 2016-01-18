package factories;

import net.dev4life.baovietyet.R;

import java.util.ArrayList;
import java.util.List;

import model.BaseUrl;
import model.Newspaper;
import model.ObjectNewspaper;
import ulti.HandleRssDantri;
import ulti.HandleRssTinhte;
import ulti.HandleRssVietnamnet;
import ulti.HandleRssVnexpress;
import ulti.IHandleRss;

/**
 * Created by DEV4LIFE on 1/16/16.
 */
public class ListNewspaper {

    private static String[] titleSections = {
            "Vnexpress",
            "Dân Trí",
            "Vietnamnet",
            "Tinh tế",
            "Ngôi sao"
    };
    private static int[] iconSections = {
            R.drawable.ic_vnexpress_24dp,
            R.drawable.ic_dantri_24dp,
            R.drawable.ic_vietnamnet,
            R.drawable.ic_tinhte,
            R.drawable.ic_ngoisao
    };

    private static CharSequence[][] titlePaper ={
            Newspaper.TITLE_VNEXPRESS,
            Newspaper.TITLE_DANTRI,
            Newspaper.TITLE_VIETNAMNET,
            Newspaper.TITLE_TINHTE,
            Newspaper.TITLE_NGOISAO
    };

    private static CharSequence[][] titleRssPaper ={
            Newspaper.TITLE_RSS_VNEXPRESS,
            Newspaper.TITLE_RSS_DANTRI,
            Newspaper.TITLE_RSS_VIETNAMNET,
            Newspaper.TITLE_RSS_TINHTE,
            Newspaper.TITLE_RSS_NGOISAO
    };

    private static String[] urls = {
            BaseUrl.VNEXPRESS.toString(),
            BaseUrl.DANTRI.toString(),
            BaseUrl.VIETNAMNET.toString(),
            BaseUrl.TINHTE.toString(),
            BaseUrl.NGOISAO.toString()
    };

    private static IHandleRss[] handleRss = {
            HandleRssVnexpress.newInstance(),
            HandleRssDantri.newInstance(),
            HandleRssVietnamnet.newInstance(),
            HandleRssTinhte.newInstance(),
            HandleRssVnexpress.newInstance()
    };

    public static List<ObjectNewspaper> newInstance() {
        List<ObjectNewspaper> list = new ArrayList<>();

        for(int i=0;i<titleSections.length;i++){
            list.add(new ObjectNewspaper(titleSections[i],
                    iconSections[i],
                    titlePaper[i],
                    titleRssPaper[i],
                    urls[i],
                    handleRss[i]));
        }
        return list;
    }
}
