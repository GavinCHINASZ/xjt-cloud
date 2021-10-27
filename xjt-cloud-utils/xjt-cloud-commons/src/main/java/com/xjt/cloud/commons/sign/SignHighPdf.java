package com.xjt.cloud.commons.sign;

import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.security.*;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.xjt.cloud.commons.utils.SysLog;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;

/**
 * 在已有的PDF上签章
 *
 * @author huanggc
 * @date 2021/05/10
 */
public class SignHighPdf {

    /**
     * @param password     秘钥密码
     * @param keyStorePath 秘钥文件路径
     * @param signPdfSrc   签名的PDF文件
     * @param x            坐标
     * @param y            坐标
     * @return byte[]
     */
    private static byte[] sign(String password, String keyStorePath, String signPdfSrc, float x, float y, int page, String signText) {
        File signPdfSrcFile = new File(signPdfSrc);
        PdfReader reader;
        ByteArrayOutputStream signPDFData = null;
        PdfStamper stp;
        FileInputStream fos = null;
        try {
            BouncyCastleProvider provider = new BouncyCastleProvider();
            Security.addProvider(provider);
            KeyStore ks = KeyStore.getInstance("PKCS12", new BouncyCastleProvider());
            fos = new FileInputStream(keyStorePath);
            // 私钥密码
            ks.load(fos, password.toCharArray());
            String alias = (String) ks.aliases().nextElement();
            PrivateKey key = (PrivateKey) ks.getKey(alias, password.toCharArray());
            Certificate[] chain = ks.getCertificateChain(alias);
            reader = new PdfReader(signPdfSrc);
            signPDFData = new ByteArrayOutputStream();
            // 临时pdf文件
            File temp = new File(signPdfSrcFile.getParent(), System.currentTimeMillis() + ".pdf");
            stp = PdfStamper.createSignature(reader, signPDFData, '\0', temp, true);

            PdfSignatureAppearance sap = stp.getSignatureAppearance();
            sap.setReason("数字签名，不可改变");
            // 是对应x轴和y轴坐标, page第几页
            sap.setVisibleSignature(new Rectangle(x, y, x + 130, y + 70), page, "sr" + String.valueOf(System.nanoTime()));
            // layer 0 Creating the appearance for layer 0
            // 方框
            PdfTemplate n0 = sap.getLayer(0);
            n0.reset();
            float lx = n0.getBoundingBox().getLeft();
            float by = n0.getBoundingBox().getBottom();
            // 130
            float width = n0.getBoundingBox().getWidth() - 20;
            // 70
            float height = n0.getBoundingBox().getHeight() - 16;
            // 颜色
            n0.setRGBColorFill(215, 80, 97);
            //左
            n0.rectangle(lx, by, 2, height);
            // 下
            n0.rectangle(lx, by, width, 2);
            // 上
            n0.rectangle(lx, by + height - 2, width, 2);
            // 右
            n0.rectangle(lx + width - 2, by, 2, height);
            n0.fill();

            // layer 2
            PdfTemplate n2 = sap.getLayer(2);
            n2.setCharacterSpacing(0.0f);
            ColumnText ct = new ColumnText(n2);
            ct.setSimpleColumn(n2.getBoundingBox());
            n2.setRGBColorFill(215, 80, 97);

            BaseColor baseColor = new BaseColor(215, 80, 97);
            // 做一个占位的动作
            Paragraph p1 = new Paragraph(" ");
            BaseFont bf = BaseFont.createFont(AsianFontMapper.ChineseSimplifiedFont, AsianFontMapper.ChineseSimplifiedEncoding_H, BaseFont.NOT_EMBEDDED);
            Font font1 = new Font(bf, 12, Font.BOLD, baseColor);
            p1.setFont(font1);
            ct.addElement(p1);

            // 方框内的文字
            Font font2 = new Font(bf, 13, Font.BOLD, baseColor);
            Paragraph p = new Paragraph(12);
            p.add(signText);
            // (居中)Element.ALIGN_CENTER  文字对齐方式
            p.setAlignment(Element.ALIGN_CENTER);
            p.setFont(font2);
            // 左右边距
            p.setIndentationLeft(2);
            p.setIndentationRight(22);

            ct.addElement(p);
            ct.go();
            stp.getWriter().setCompressionLevel(PdfStream.BEST_COMPRESSION);
            ExternalDigest digest = new BouncyCastleDigest();
            ExternalSignature signature = new PrivateKeySignature(key, DigestAlgorithms.SHA512, provider.getName());
            MakeSignature.signDetached(sap, digest, signature, chain, null, null, null, 0, CryptoStandard.CADES);
            stp.close();
            reader.close();

            return signPDFData.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (signPDFData != null) {
                try {
                    signPDFData.close();
                } catch (IOException e) {
                    SysLog.error(e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    SysLog.error(e);
                }
            }
        }
        return null;
    }

    /**
     * pdf签章方法
     *
     * @param fileUrl       文件路径
     * @param pdfName       pdfName
     * @param signText      内容
     * @param outputPdfName 签章后的pdf名称
     * @param page          在pdf的第几页签章
     * @author huanggc
     * @date 2021/05/10
     */
    public static void signMethod(String fileUrl, String pdfName, String signText, String outputPdfName, int page) {
        // 签章
        // 对已经签章的signed.pdf文件再次签章，这次是高清签章
        byte[] fileData = sign("123456", fileUrl + "keystore.p12", fileUrl + pdfName + ".pdf", 376, 262, page, signText);

        FileOutputStream f = null;
        try {
            f = new FileOutputStream(new File(fileUrl + outputPdfName + ".pdf"));
        } catch (Exception e) {
            SysLog.error(e);
        }

        if (f != null && fileData != null) {
            try {
                f.write(fileData);
            } catch (IOException e) {
                SysLog.error(e);
            }
            try {
                f.close();
            } catch (IOException e) {
                SysLog.error(e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String url = "F:\\21ideaWord\\xjtnettycloud\\xjtnettycloud\\xjt-cloud-check-maintenance-manage\\xjt-cloud-maintenance-web\\config\\static\\doc\\";
        // 对已经签章的signed.pdf文件再次签章，这次是高清签章
        signMethod(url, "1620722793201", "中国人民共和国\n一级消防工程师\n黄佳明\n14418000097", "signed2", 3);
    }

}
