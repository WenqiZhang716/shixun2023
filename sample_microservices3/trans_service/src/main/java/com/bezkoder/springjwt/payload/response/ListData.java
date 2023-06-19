package com.bezkoder.springjwt.payload.response;

import java.util.List;

public class ListData {
//        private int code;
        private List<?> list;

        public ListData(List<?> list) {
//            this.code=code;
            this.list=list;
        }

//        public int getCode() {
//            return code;
//        }
//
//        public void setCode(int code) {
//            this.code = code;
//        }

        public List<?> getlist() {
            return list;
        }

        public void setlist(List<?> data) {
            this.list = data;
        }

}
