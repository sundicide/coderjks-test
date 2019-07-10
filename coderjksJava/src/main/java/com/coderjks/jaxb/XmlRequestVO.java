package com.coderjks.jaxb;

import javax.xml.bind.annotation.*;

/**
 * 아래와 같은 형태의 xml을 만들어서 보내야 한다고 하자.
 *
 * <?xml version="1.0" encoding="UTF-8"?>
 * <request>
 *     <data>
 *         <ID>MYID</ID>
 *         <PW>MYPW</PW>
 *     </data>
 * </request>
 */

@XmlRootElement(name="request")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlRequestVO {
    @XmlElement(name="data")
    protected XmlRequestData xmlRequestData;

    public XmlRequestData getXmlRequestData() {
        return xmlRequestData;
    }

    public void setXmlRequestData(XmlRequestData xmlRequestData) {
        this.xmlRequestData = xmlRequestData;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "id",
            "pw"
    })
    public static class XmlRequestData {
        @XmlElement(name="ID")
        protected String id;

        @XmlElement(name="pw")
        protected String pw;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPw() {
            return pw;
        }

        public void setPw(String pw) {
            this.pw = pw;
        }
    }
}
