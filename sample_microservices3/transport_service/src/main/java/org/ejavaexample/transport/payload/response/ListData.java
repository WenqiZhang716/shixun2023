package org.ejavaexample.transport.payload.response;

import java.util.List;

public class ListData {
//        private int code;
        private List<?> list;

        public ListData(List<?> list) {
//            this.code=code;
            this.list=list;
        }


        public List<?> getlist() {
            return list;
        }

        public void setlist(List<?> data) {
            this.list = data;
        }

}
