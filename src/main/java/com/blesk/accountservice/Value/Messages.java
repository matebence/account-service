package com.blesk.accountservice.Values;

public class Messages {

    public static final String TYPE_MISMATCH_EXCEPTION = "Nesprávný formát URL adresi";
    public static final String REQUEST_BODY_NOT_FOUND_EXCEPTION = "Prázdna požiadavka";
    public static final String PAGE_NOT_FOUND_EXCEPTION = "Je nám ľúto, ale požadovaná stránka nebola nájdená";
    public static final String NULL_POINTER_EXCEPTION = "Ľutujeme, ale nastala chyba";
    public static final String AUTH_EXCEPTION = "Ľutujeme, ale stránka nie je k dispozícií";
    public static final String AUTH_REQUIRED_EXCEPTION = "Prístup odmietnutý";
    public static final String SQL_EXCEPTION = "Operácia sa neuskutočnila";
    public static final String EXCEPTION = "Nastala neočakávaná chyba";
    public static final String PAGINATION_EXCEPTION = "Požiadavku sa nepodarilo spracv";
    public static final String INDEX_NOT_FOUND_EXCEPTION = "Požiadavku sa nepodarilo spracovať";

    public static final String CREATE_ROLE = "Vytvorenie roli sa nepodarilo";
    public static final String DELETE_GET_ROLE = "Ľutujeme, ale rola neexistuje";
    public static final String DELETE_ROLE = "Odstránenie roli sa nepodarilo";
    public static final String UPDATE_ROLE = "Rolu sa nepodarilo aktualizovať";
    public static final String GET_ROLE = "Ľutujeme, rola neexistuje";
    public static final String GET_ALL_ROLES = "Nenašiel sa žiadna rola";
    public static final String GET_ROLE_BY_NAME = "Ľutujeme, rola neexistuje";
    public static final String GET_ROLE_PRIVILEGES = "Rola %s nemá žiadné práva";

    public static final String CREATE_PREFERENCE = "Vytvorenie preferencie sa nepodarilo";
    public static final String DELETE_GET_PREFERENCE = "Ľutujeme, ale preferencia neexistuje";
    public static final String DELETE_PREFEREBCE = "Odstránenie preferencie sa nepodarilo";
    public static final String UPDATE_PREFEREBCE = "Preferenciu sa nepodarilo aktualizovať";
    public static final String GET_PREFERENCE = "Ľutujeme, preferencia neexistuje";
    public static final String GET_ALL_PREFERENCES = "Nenašiel sa žiadné preferencie";
    public static final String GET_PREFERENCE_BY_NAME = "Ľutujeme, preferencia neexistuje";

    public static final String CREATE_PASSWORD_TOKEN = "Vytvorenie kľúču pre zabudnuté heslo sa nepodarilo";
    public static final String DELETE_GET_PASSWORD_TOKEN = "Ľutujeme, ale kľúču pre zabudnuté heslo neexistuje";
    public static final String DELETE_PASSWORD_TOKEN = "Odstránenie kľúču pre zabudnuté heslo sa nepodarilo";
    public static final String UPDATE_PASSWORD_TOKEN = "Kľúč pre zabudnuté heslo sa nepodarilo aktualizovať";
    public static final String GET_PASSWORD_TOKEN = "Ľutujeme, kľúču pre zabudnuté heslo neexistuje";

    public static final String CREATE_PRIVILEGE = "Vytvorenie práva sa nepodarilo";
    public static final String DELETE_GET_PRIVILEGE = "Ľutujeme, ale právo neexistuje";
    public static final String DELETE_PRIVILEGE = "Odstránenie práva sa nepodarilo";
    public static final String UPDATE_PRIVILEGE = "Právo sa nepodarilo aktualizovať";
    public static final String GET_PRIVILEGE = "Ľutujeme, právo neexistuje";
    public static final String GET_ALL_PRIVILEGES = "Nenašiel sa žiadné práva";
    public static final String GET_PRIVILEGE_BY_NAME = "Ľutujeme, právo neexistuje";

    public static final String CREATE_GET_ACCOUNT = "Požadovaná rola pre vytvorenie nového úctu sa nenašla";
    public static final String CREATE_ACCOUNT = "Nepodarilo sa vytvoriť nový účet";
    public static final String DELETE_GET_ACCOUNT = "Ľutujeme, ale účet nebol nájdení";
    public static final String DELETE_ACCOUNT = "Odstránenie účtu bolo neúspešné";
    public static final String UPDATE_ACCOUNT = "Aktualizovanie účtu bolo neúspešné";
    public static final String GET_ACCOUNT = "Ľutujeme, účet neexistuje";
    public static final String GET_ALL_ACCOUNTS = "Nenašiel sa žiadný účet";
    public static final String GET_ACCOUNT_INFORMATION = "Ľutujeme, účet neexistuje";
    public static final String FIND_ACCOUNT_BY_EMAIL = "Ľutujeme, emailová adresa neexistuje";
    public static final String GET_ROLES_TO_ACCOUNT = "Ľutujeme, chyba účtu";
    public static final String SEARCH_FOR_ACCOUNT = "Kritériám nevyhoveli žiadné záznamy";

    public static final String ENTITY_IDS = "Nesprávny formát identifikačného čísla";
    public static final String ENTITY_CREATOR_ID = "Identifikačné číslo vytvárajúcého používateľa nebol nastavení";

    public static final String ACCOUNTS_USER_NAME_NULL = "Nezadali ste používateľské meno";
    public static final String ACCOUNTS_USER_NAME_LENGHT = "Používateľské meno je príliž krátké alebo dlhé";
    public static final String ACCOUNTS_PASSWORD_NULL = "Nezadali ste heslo";
    public static final String ACCOUNTS_EMAIL_NULL = "Nezadali ste emailovú adresu";
    public static final String ACCOUNTS_EMAIL_LENGHT = "Emailová adresa je príliž krátka alebo dlhá";
    public static final String ACCOUNTS_EMAIL = "Nesprávný formát emailovej adresy";

    public static final String PRIVILEGES_NULL = "Nezadali ste názov práva";
    public static final String PRIVILEGES_SIZE = "Názov práva je príliž krátké alebo dlhé";

    public static final String ROLES_NULL = "Nezadali ste meno role";
    public static final String ROLES_SIZE = "Meno roli je príliž krátké alebo dlhé";

    public static final String PREFERENCES_NULL = "Nezadali ste názov preferencie";
    public static final String PREFERENCES_SIZE = "Názov preferencie je príliž krátké alebo dlhé";

    public static final String PASSWORDS_TOKEN_NULL = "Nebol vygenerovaný token pre obnoveniu hesla";
    public static final String PASSWORDS_DATE_NULL = "Nebol nastavený dátum splatnosti pre token na obnoveniu hesla";
    public static final String PASSWORDS_DATE_VALID = "Nesprávný formát dátumu";

    public static final String LOGIN_TIMESTAMP_NULL = "Pri prihlasovaní nebol zaznamenaný dátum";
    public static final String LOGIN_IP_ADDRESS_NULL = "Pri prihlasovaní nebola zaznamenaná IP adresa";

    public static final String ACCOUNT_SIGN_IN_VERIFY = "Používateľské údaje odoslané na spracovanie";
    public static final String ACCOUNT_SIGN_IN_ERROR = "Používateľské údaje sa nepodarilo poslať na spracovanie";
    public static final String ACCOUNT_FORGET_PASSWORD = "Postup obnovenia hesla Vám bol zaslaný na uvedenú emailovú adresu";
    public static final String ACCOUNT_NEW_PUBLIC = "Účet bol vytvorený, na emailovú sme Vám poslali aktivačný odkaz";
    public static final String ACCOUNT_NEW_ERROR = "Vytvorenie účtu sa nepodarilo";
    public static final String ACCOUNT_FORGET_PASS = "Prosím zadajte nové heslo";
    public static final String ACCOUNT_FORGET_ERROR = "Možnosť obnovenia zabudnutého hesla sa nepodarilo";
}