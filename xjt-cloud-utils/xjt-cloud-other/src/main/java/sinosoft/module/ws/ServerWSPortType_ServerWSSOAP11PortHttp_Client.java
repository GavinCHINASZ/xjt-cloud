package sinosoft.module.ws;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import sinosoft.module.ws.xsd.GetFireUserInfoResponse;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;


/**
 * This class was generated by Apache CXF 3.2.4
 * 2018-07-17T10:34:07.153+08:00
 * Generated source version: 3.2.4
 *
 * @author
 */
public final class ServerWSPortType_ServerWSSOAP11PortHttp_Client {

    private static final QName SERVICE_NAME = new QName("http://ws.module.sinosoft.com", "serverWS");

    private ServerWSPortType_ServerWSSOAP11PortHttp_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = ServerWS.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        ServerWS ss = new ServerWS(wsdlURL, SERVICE_NAME);
        ServerWSPortType port = ss.getServerWSSOAP11PortHttp();

        {
            System.out.println("Invoking getCorpResult...");
            java.lang.String _getCorpResult_signMark = "";
            java.lang.String _getCorpResult_signStr = "";
            java.lang.String _getCorpResult_mark = "";
            java.lang.String _getCorpResult__return = port.getCorpResult(_getCorpResult_signMark, _getCorpResult_signStr, _getCorpResult_mark);
            System.out.println("getCorpResult.result=" + _getCorpResult__return);
        }

        {
            System.out.println("Invoking findLableFlowInfo...");
            java.lang.String _findLableFlowInfo_labelId = "";
            java.lang.String _findLableFlowInfo_deptId = "";
            java.lang.String _findLableFlowInfo_productName = "";
            java.lang.String _findLableFlowInfo__return = port.findLableFlowInfo(_findLableFlowInfo_labelId, _findLableFlowInfo_deptId, _findLableFlowInfo_productName);
            System.out.println("findLableFlowInfo.result=" + _findLableFlowInfo__return);
        }

        {
            System.out.println("Invoking getTipstaffResult...");
            java.lang.String _getTipstaffResult_signMark = "";
            java.lang.String _getTipstaffResult_signStr = "";
            java.lang.String _getTipstaffResult_mark = "";
            java.lang.String _getTipstaffResult__return = port.getTipstaffResult(_getTipstaffResult_signMark, _getTipstaffResult_signStr, _getTipstaffResult_mark);
            System.out.println("getTipstaffResult.result=" + _getTipstaffResult__return);
        }

        {
            System.out.println("Invoking getLabelInfoByCode...");
            java.lang.String _getLabelInfoByCode_labelCode = "";
            java.lang.String _getLabelInfoByCode__return = port.getLabelInfoByCode(_getLabelInfoByCode_labelCode);
            System.out.println("getLabelInfoByCode.result=" + _getLabelInfoByCode__return);
        }

        {
            System.out.println("Invoking notifyServerDelFileNew...");
            java.lang.String _notifyServerDelFileNew_signMark = "";
            java.lang.String _notifyServerDelFileNew_signStr = "";
            java.lang.String _notifyServerDelFileNew_downloadMark = "";
            java.lang.String _notifyServerDelFileNew__return = port.notifyServerDelFileNew(_notifyServerDelFileNew_signMark, _notifyServerDelFileNew_signStr, _notifyServerDelFileNew_downloadMark);
            System.out.println("notifyServerDelFileNew.result=" + _notifyServerDelFileNew__return);
        }

        {
            System.out.println("Invoking downloadOnlineNew...");
            java.lang.String _downloadOnlineNew_signMark = "";
            java.lang.String _downloadOnlineNew_signStr = "";
            byte[] _downloadOnlineNew_ruleContent = new byte[0];
            java.lang.String _downloadOnlineNew__return = port.downloadOnlineNew(_downloadOnlineNew_signMark, _downloadOnlineNew_signStr, _downloadOnlineNew_ruleContent);
            System.out.println("downloadOnlineNew.result=" + _downloadOnlineNew__return);
        }

        {
            System.out.println("Invoking getProjectLog...");
            java.lang.String _getProjectLog_signMark = "";
            java.lang.String _getProjectLog_signStr = "";
            java.lang.String _getProjectLog__return = port.getProjectLog(_getProjectLog_signMark, _getProjectLog_signStr);
            System.out.println("getProjectLog.result=" + _getProjectLog__return);
        }

        {
            System.out.println("Invoking getFireUserInfo...");
            try {
                GetFireUserInfoResponse _getFireUserInfo__return = port.getFireUserInfo();
                System.out.println("getFireUserInfo.result=" + _getFireUserInfo__return);

            } catch (GetFireUserInfoFault e) {
                System.out.println("Expected exception: getFireUserInfoFault has occurred.");
                System.out.println(e.toString());
            }
        }

        {
            System.out.println("Invoking getRandomSignTextNew...");
            java.lang.String _getRandomSignTextNew_mark = "";
            java.lang.String _getRandomSignTextNew__return = port.getRandomSignTextNew(_getRandomSignTextNew_mark);
            System.out.println("getRandomSignTextNew.result=" + _getRandomSignTextNew__return);
        }

        {
            System.out.println("Invoking updateQueryLable1...");
            java.lang.String _updateQueryLable1_ipAddress = "";
            java.lang.String _updateQueryLable1_lableId = "";
            port.updateQueryLable1(_updateQueryLable1_ipAddress, _updateQueryLable1_lableId);
        }

        {
            System.out.println("Invoking getUnitDataNew...");
            java.lang.String _getUnitDataNew_signMark = "";
            java.lang.String _getUnitDataNew_signStr = "";
            java.lang.String _getUnitDataNew_date = "";
            java.lang.String _getUnitDataNew__return = port.getUnitDataNew(_getUnitDataNew_signMark, _getUnitDataNew_signStr, _getUnitDataNew_date);
            System.out.println("getUnitDataNew.result=" + _getUnitDataNew__return);
        }

        {
            System.out.println("Invoking onlineCheck...");
            java.lang.String _onlineCheck_userId = "";
            java.lang.String _onlineCheck_deptId = "";
            java.lang.String _onlineCheck_labelCode = "";
            try {
                java.lang.String _onlineCheck__return = port.onlineCheck(_onlineCheck_userId, _onlineCheck_deptId, _onlineCheck_labelCode);
                System.out.println("onlineCheck.result=" + _onlineCheck__return);
            } catch (OnlineCheckFault e) {
                System.out.println("Expected exception: onlineCheckFault has occurred.");
                System.out.println(e.toString());
            }
        }

        {
            System.out.println("Invoking getClientVer...");
            java.lang.String _getClientVer_ver = "";
            java.lang.String _getClientVer__return = port.getClientVer(_getClientVer_ver);
            System.out.println("getClientVer.result=" + _getClientVer__return);
        }

        {
            System.out.println("Invoking existsUserPhone...");
            java.lang.String _existsUserPhone_userPhone = "";
            java.lang.Boolean _existsUserPhone__return = port.existsUserPhone(_existsUserPhone_userPhone);
            System.out.println("existsUserPhone.result=" + _existsUserPhone__return);
        }

        {
            System.out.println("Invoking getListNew...");
            java.lang.String _getListNew_signMark = "";
            java.lang.String _getListNew_signStr = "";
            byte[] _getListNew_xmlList = new byte[0];
            byte[] _getListNew__return = port.getListNew(_getListNew_signMark, _getListNew_signStr, _getListNew_xmlList);
            System.out.println("getListNew.result=" + _getListNew__return);
        }

        {
            System.out.println("Invoking reUploadNew...");
            java.lang.String _reUploadNew_mark = "";
            java.lang.String _reUploadNew_signStr = "";
            byte[] _reUploadNew_content = new byte[0];
            java.lang.String _reUploadNew__return = port.reUploadNew(_reUploadNew_mark, _reUploadNew_signStr, _reUploadNew_content);
            System.out.println("reUploadNew.result=" + _reUploadNew__return);
        }

        {
            System.out.println("Invoking uploadNew...");
            java.lang.String _uploadNew_mark = "";
            java.lang.String _uploadNew_signStr = "";
            byte[] _uploadNew_content = new byte[0];
            java.lang.String _uploadNew__return = port.uploadNew(_uploadNew_mark, _uploadNew_signStr, _uploadNew_content);
            System.out.println("uploadNew.result=" + _uploadNew__return);
        }

        {
            System.out.println("Invoking isSuedUsbKey...");
            java.lang.String _isSuedUsbKey_deptid = "";
            java.lang.String _isSuedUsbKey_deptName = "";
            java.lang.String _isSuedUsbKey__return = port.isSuedUsbKey(_isSuedUsbKey_deptid, _isSuedUsbKey_deptName);
            System.out.println("isSuedUsbKey.result=" + _isSuedUsbKey__return);
        }

        System.exit(0);
    }

}
