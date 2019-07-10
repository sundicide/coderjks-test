package com.coderjks.jaxb;

import javax.xml.bind.annotation.*;

/**
 * 아래와 같은 형태의 xml을 만들어서 보내야 한다고 하자.
 *
 * <?xml version="1.0" encoding="UTF-8"?>
 * <response>
 *     <data>
 *         <MSG>GOOD</MSG>
 *     </data>
 * </response>
 */

@XmlRootElement(name="response")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlResponseVO {
    @XmlElement(name="data")
    protected XmlResponseData xmlRequestData;

    public XmlResponseData getXmlRequestData() {
        return xmlRequestData;
    }

    public void setXmlRequestData(XmlResponseData xmlRequestData) {
        this.xmlRequestData = xmlRequestData;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "msg"
    })
    public static class XmlResponseData {
        @XmlElement(name="MSG")
        protected String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
