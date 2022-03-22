package shop.daijian.common.constant;

import static shop.daijian.common.constant.GlobalConstant.GATEWAY_URL;

/**
 * 支付宝常量
 *
 * @author qiyubing
 * @since 2019-02-14
 */
public class AlipayConstant {

    public static final String APP_ID = "2016091800537071";

    public static final String ALIPAY_GATEWAY = "https://openapi.alipaydev.com/GATEWAY.do";

    public static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCcODVcsDeyUV4/cu4//2kAX8J9Dgc2/PLf7uYPWpUUmlVRnDc1TdnY60z3Zl3SckmhC8SCPz8ttOe8VlTEl3wmVeVri89rzifk66BT9yMo65JbbN4r3JJHUkjEYaA98kEyWGbRnNNscJCuwPGojuJHfyrwJfF2VxELflkXP3Ejed6DAsGVToXvfa42OceJhWRrMaw5KYu8zKpqN3w953obbRJE/t7b2FaekPVOAQnmEZdp69Jx1tqhij3npdBrsGSJGy1xax3EeIU+pzeLrybS6EqZ0U51NeA9Va2qkrE9DcQHqIP3sMCqaEZ+kVRf9fCp5Ypui221AWzcXR1tIV/dAgMBAAECggEAcVAwHLBRZg2xUFKcUF2CG1xaZX00bIVj4lyWEZMByfT8Z/ro7MgweTCMHnPpIAn4MFGp01PvDsdGxAzW5dZ/ngr2I2FBLhBWTa1AGUeNAFSHT7uTF7uhewxLbe7mXaiH8+29ftPDY8fummOnpj0mEvhB+/l5wP38hhq6oqtI9JJ/N9ylMhnFFB26btBRtoLEz6imAgjKPHToXZD/I07WkgFQoKNdk4X4/0b4iZ0BFjGbMkM/oNiaM4pEkqa/T82OBsiK/6hohLGbB2xAsgpc+u7wJtvibTU9RQhNQVu+kMD428X91te1G3cs1wyGWds/xzvXkC7k7WLCugXFv0lQXQKBgQDnu0e+nfcvbvVFZhSEkhTLfaU4QndxjTUK+1/p1sJyBcgxLXjNSqcuJ+tdhCrIihbYMoDv2nScq/3QmVFPdvjIoTMPPFaZW9jt3eTc2dy3FbF8h7tiQX+K2yW5myz4TcbBVoWmfRMJlcmcIqZ5Q3xiIPamyOL1hu7skdQPkoVQNwKBgQCslHPfKozKjNFVe32EoVOeEY1ZRqLI/D+NimAD0LOeRoK9E007MipzWYzkQvnSQSmWA7CEcgT6wWk/7hfrZ115/NHv7NhfnEtjetlvWHgajDxD4q8ow6xeQcwTkNwFb22PeEo9L+GxnRIJvIyDsgEv3q9cNmw1JHuWv7okZXq+iwKBgALjicxtibRUrMgVsrHIZGv5AANIiNaojP27DoIKxFfJjKpYR8Y/ZQAlTUDYMfRYDFrbnlHfJk8BseNU1feoowdVQx6wgN/ejkDA4GaEbMT3h0PnKUXbTSy8+KZssc3SomSsoHPn4UHgVSgWIn8mgTlz8b84h+PJr9bRJpSfHfxXAoGBAJ0BpfUm+dWgjSXO5l+Ogcsxvr201I4zZwI3HKj76x2YmoK3l7C+L/6nPtW/1RYisFnJW4LzVHJpGX7O9rX0YQeFKd20RRme0LtdXYm+G+5THNTJ238jnaJXC/nU9UaAnFVFI41NDXCQLIPtJIz/mbLaTgF/haxHV6TLIubP5/6HAoGASTnI7cKaZE+wDHigj6epWXDxrbSoJYeFvW5s2HWIdGtACIahhYjQRFpk/ygjkeBp1CpHkNqOvdaFOEZmFGG3gTMsNnk6h31Rbf4Oqsktqh/NRxqhIofH5S4y1U6ekLvLTC0/K68XAalUbVmN5x7p+oupBnjlyrTVpjeJ7RE0O7c=";

    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1X6HewAYya8ZXMcydvFmtva1BXybStyuGAJ233qz/vfHZD9XgDqK70RKisLCQNNbUFfQv0NdXOGPZLOVC6NCnu9K28qUZ4mCMfmmgrczq2+F3m8y+iEawCg2ddWhaWYgWfDXCZt21tBTHz/pmRNx9xdghP304ssVkcnpUeTixYD1SrYU7NQ/6M+5gAHj12zhJbX5F4R4yEupZgU2qzuMk6pY5XzcZg178GaBtvHkfRrLXCxWLrnURXUxGcP9bmqe9Hf6EN1Pi7F5VAyH6KK/1m98rQ600i65OPDjdrcyN8xG+m8nO9U67cbUkfeJSu9LPVV9jfzVwF/nxAPtfxsguQIDAQAB";

    public static final String TRADE_NOTIFY_URL = GATEWAY_URL + "/trade/alipay/notify";

    public static final String REDIRECT_URI = "http://39.106.14.147:8080/ynec/api/auth/alipay/redirect";

    public static final String FORMAT = "json";

    public static final String CHARSET = "UTF-8";

    public static final String SIGN_TYPE = "RSA2";

}
