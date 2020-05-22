package com.blesk.accountservice.Value;

public class Messages {
    public static final String PASSWORD_RULES = "Nepodarilo sa načítať validačný súbor";



    public static final String TYPE_MISMATCH_EXCEPTION = "Nesprávný formát URL adresi";
    public static final String REQUEST_BODY_NOT_FOUND_EXCEPTION = "Prázdna požiadavka";
    public static final String PAGE_NOT_FOUND_EXCEPTION = "Je nám ľúto, ale požadovaná stránka nebola nájdená";
    public static final String NULL_POINTER_EXCEPTION = "Ľutujeme, ale nastala chyba";
    public static final String AUTH_EXCEPTION = "Ľutujeme, ale stránka nie je k dispozícií";
    public static final String AUTH_REQUIRED_EXCEPTION = "Prístup odmietnutý";
    public static final String SQL_EXCEPTION = "Operácia sa neuskutočnila";
    public static final String EXCEPTION = "Nastala neočakávaná chyba";



    public static final String UNIQUE_FIELD_DEFAULT = "Obsah pola nie je jedinečné";
    public static final String PASSWORD_FIELD_DEFAULT = "Heslo nie je dostačne bezpecné";
    public static final String FIELD_MATCH_DEFAULT = "Dve zvolené stlpce sa nezhodujú";

    public static final String ACCOUNTS_USER_NAME_NULL = "Nezadali ste používateľské meno";
    public static final String ACCOUNTS_USER_NAME_LENGHT = "Používateľské meno je príliž krátké alebo dlhé";
    public static final String ACCOUNTS_USER_NAME_UNIQUE = "Používatelské meno už existuje";
    public static final String ACCOUNTS_PASWORD_MATCH = "Heslá sa nezhodujú";
    public static final String ACCOUNTS_PASSWORD_NULL = "Nezadali ste heslo";
    public static final String ACCOUNTS_EMAIL_NULL = "Nezadali ste emailovú adresu";
    public static final String ACCOUNTS_EMAIL_LENGHT = "Emailová adresa je príliž krátka alebo dlhá";
    public static final String ACCOUNTS_EMAIL_UNIQUE = "Emailová adresa už existuje";
    public static final String ACCOUNTS_EMAIL = "Nesprávný formát emailovej adresy";

    public static final String ACTIVATIONS_TOKEN_NULL = "Nebol vygenerovaný kľúč pre aktiváciu účtu";

    public static final String LOGIN_TIMESTAMP_NULL = "Pri prihlasovaní nebol zaznamenaný dátum";
    public static final String LOGIN_IP_ADDRESS_NULL = "Pri prihlasovaní nebola zaznamenaná IP adresa";

    public static final String PASSWORDS_TOKEN_NULL = "Nebol vygenerovaný kľúč pre obnoveniu hesla";
    public static final String PASSWORDS_DATE_NULL = "Nebol nastavený dátum splatnosti pre kľúč na obnoveniu hesla";
    public static final String PASSWORDS_DATE_VALID = "Nesprávný formát dátumu";

    public static final String PRIVILEGES_NULL = "Nezadali ste názov práva";
    public static final String PRIVILEGES_SIZE = "Názov práva je príliž krátký alebo dlhý";
    public static final String PRIVILEGES_UNIQUE = "Názov práva už existuje";

    public static final String ROLES_NULL = "Nezadali ste meno roli";
    public static final String ROLES_SIZE = "Meno roli je príliž krátké alebo dlhé";
    public static final String ROLES_UNIQUE = "Názov roli už existuje";

    public static final String PREFERENCES_NULL = "Nezadali ste názov preferencie";
    public static final String PREFERENCES_SIZE = "Názov preferencie je príliž krátké alebo dlhé";
    public static final String PREFERENCES_UNIQUE = "Názov preferencie už existuje";



    public static final String PAGINATION_ERROR = "Požiadavku sa nepodarilo spracovať, chyba stránkovania";
    public static final String SEARCH_ERROR = "Kritériám nevyhoveli žiadné záznamy";

    public static final String CREATE_ACCOUNT = "Nepodarilo sa vytvoriť nový účet, skúste znova";
    public static final String DELETE_ACCOUNT = "Odstránenie účtu bolo neúspešné, skúste znova";
    public static final String UPDATE_ACCOUNT = "Aktualizovanie účtu bolo neúspešné, skúste znova";
    public static final String GET_ACCOUNT = "Ľutujeme, účet neexistuje";
    public static final String GET_ALL_ACCOUNTS = "Nenašiel sa žiadný účet";

    public static final String CREATE_PREFERENCE = "Vytvorenie preferencie sa nepodarilo, skúste znova";
    public static final String DELETE_PREFERENCE = "Odstránenie preferencie sa nepodarilo, skúste znova";
    public static final String UPDATE_PREFERENCE = "Preferenciu sa nepodarilo aktualizovať, skúste znova";
    public static final String GET_PREFERENCE = "Ľutujeme, preferencia neexistuje";
    public static final String GET_ALL_PREFERENCES = "Nenašiel sa žiadné preferencie";

    public static final String CREATE_PRIVILEGE = "Vytvorenie práva sa nepodarilo, skúste znova";
    public static final String DELETE_PRIVILEGE = "Odstránenie práva sa nepodarilo, skúste znova";
    public static final String UPDATE_PRIVILEGE = "Právo sa nepodarilo aktualizovať, skúste znova";
    public static final String GET_PRIVILEGE = "Ľutujeme, právo neexistuje";
    public static final String GET_ALL_PRIVILEGES = "Nenašiel sa žiadné práva";

    public static final String CREATE_ROLE = "Vytvorenie roli sa nepodarilo, skúste znova";
    public static final String DELETE_ROLE = "Odstránenie roli sa nepodarilo, skúste znova";
    public static final String UPDATE_ROLE = "Rolu sa nepodarilo aktualizovať, skúste znova";
    public static final String GET_ROLE = "Ľutujeme, rola neexistuje";
    public static final String GET_ALL_ROLES = "Nenašiel sa žiadna rola";
    public static final String GET_ROLE_PRIVILEGES = "Rola %s nemá žiadné práva";
}