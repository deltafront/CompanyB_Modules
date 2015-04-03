package companyB.encrypted.test;

import companyB.encrypted.Encrypted;

public class TestObject
{
    @Encrypted
    private String defaultEncryption = "defaultEncryption";
    @Encrypted(algorithm = Encrypted.algorithms.MD2)
    private String md2 = "md2";
    @Encrypted(algorithm = Encrypted.algorithms.MD5)
    private String md5 = "md5";
    @Encrypted(algorithm = Encrypted.algorithms.SHA1)
    protected String sha1 = "sha1";
    @Encrypted(algorithm = Encrypted.algorithms.SHA256)
    public String sha256 = "sha256";
    @Encrypted(algorithm = Encrypted.algorithms.SHA348)
    String sha348 = "sha348";
    @Encrypted(algorithm = Encrypted.algorithms.SHA512)
    String sha512= "sha512";
    String notEncrypted = "notEncrypted";
}