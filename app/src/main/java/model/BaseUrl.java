package model;

/**
 * Created by DEV4LIFE on 1/13/16.
 */
public enum BaseUrl {
    VNEXPRESS {
        public String toString() {
            return "http://vnexpress.net/rss/";
        }
    },

    DANTRI {
        public String toString() {
            return "http://dantri.com.vn";
        }
    },
    TINHTE {
        public String toString() {
            return "http://tinhte.vn/";
        }
    },
    VIETNAMNET {
        public String toString() {
            return "http://vietnamnet.vn/rss/";
        }
    },
    NGOISAO {
        public String toString() {
            return "http://ngoisao.net/rss/";
        }
    }

}
