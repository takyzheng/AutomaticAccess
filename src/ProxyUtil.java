import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.Proxy.Type;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


/**
 * 类名 ClassName  ProxyUtil
 * 项目 ProjectName  AutomaticAccess
 * 作者 Author  郑添翼 Taky.Zheng
 * 邮箱 E-mail 275158188@qq.com
 * 时间 Date  2019-07-04 10:32 ＞ω＜
 * 描述 Description TODO
 */
public class ProxyUtil {

    private static Logger logger = LogManager.getLogger("ProxyUtil");


    //HTTPS代理请求
        public static String HttpsProxy(String url, String proxy, int port) {
            HttpsURLConnection httpsConn = null;
            BufferedReader in = null;
            String result = "";
            String responseMessage = "";
            BufferedReader reader = null;
            try {
                //创建请求地址
                URL urlClient = new URL(url);
                //System.out.println("请求的URL========：" + urlClient);

                logger.info("请求的URL========：" + urlClient);

                SSLContext sc = SSLContext.getInstance("SSL");
                // 指定信任https
                sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
                //创建代理虽然是https也是Type.HTTP
                Proxy proxy1 = new Proxy(Type.HTTP, new InetSocketAddress(proxy, port));
                //设置代理
                httpsConn = (HttpsURLConnection) urlClient.openConnection(proxy1);

                //设置链接超时时间
                httpsConn.setConnectTimeout(5 * 1000);

                httpsConn.setSSLSocketFactory(sc.getSocketFactory());
                httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
                // 设置通用的请求属性
                httpsConn.setRequestProperty("accept", "*/*");
                httpsConn.setRequestProperty("connection", "Keep-Alive");
                httpsConn.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");

                //以下三行是在需要验证时，输入帐号密码信息
                String headerkey = "Proxy-Authorization";
                String headerValue = "Basic "+ Base64.encodeBase64String("275158188@qq.com:z8384674".getBytes()); //帐号密码用:隔开，base64加密方式
                httpsConn.setRequestProperty(headerkey, headerValue);

                //连接
                httpsConn.connect();


                // 定义BufferedReader输入流来读取URL的响应
                in = new BufferedReader(
                        new InputStreamReader(httpsConn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                // 断开连接
                httpsConn.disconnect();
                //System.out.println("====result===="+result);
                logger.info("====result===="+result);
                responseMessage = httpsConn.getResponseMessage();
                //System.out.println("返回结果：" + responseMessage);
                logger.info("返回结果：" + responseMessage);

            } catch (Exception e) {
                return "error";
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                }
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return responseMessage;
        }


        //普通URL
        public static String commonConnection(String url){
            HttpURLConnection httpConn = null;
            BufferedReader in = null;
            String result = "";
            BufferedReader reader = null;

            try {
                URL urlClient = new URL(url);
                System.out.println("请求的URL========：" + urlClient);

                httpConn = (HttpURLConnection) urlClient.openConnection();

                //设置链接超时时间
                httpConn.setConnectTimeout(5 * 1000);

                // 设置通用的请求属性
                httpConn.setRequestProperty("accept", "*/*");
                httpConn.setRequestProperty("connection", "Keep-Alive");
                httpConn.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");


                //连接
                try {
                    httpConn.connect();
                } catch (Exception e) {
                    return "链接错误!";
                }

                // 定义BufferedReader输入流来读取URL的响应
                in = new BufferedReader(
                        new InputStreamReader(httpConn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line + "\r\n";
                }
                // 断开连接
                httpConn.disconnect();
                System.out.println("====result====\r\n" + result);
                System.out.println("返回结果：" + httpConn.getResponseMessage());


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                }
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return result;
        }


        private static class TrustAnyTrustManager implements X509TrustManager {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[] {};
            }
        }
        private static class TrustAnyHostnameVerifier implements HostnameVerifier {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }

//        public static void main(String[] args) {
//            HttpsProxy("https://www.baidu.com/", "", "127.0.0.1", 81);
//            HttpProxy("http://www.aseoe.com/", "", "127.0.0.1", 81);
//        }

    }
