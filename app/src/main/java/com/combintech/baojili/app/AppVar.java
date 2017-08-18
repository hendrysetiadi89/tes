package com.combintech.baojili.app;

import com.combintech.baojili.model.Anggota;

/**
 * Created by server02 on 03/08/2017.
 */

public class AppVar {
    public static final String LOGIN_URL = "http://smkbifor.sch.id/baojili-api/login.php";
    public static final String LOGGER_URL = "http://smkbifor.sch.id/baojili-api/logger.php?username=";
    public static final String LOGGER_QR_URL = "http://smkbifor.sch.id/baojili-api/logger_qr.php?qrcode=";
    public static final String ANGGOTA_URL = "http://smkbifor.sch.id/baojili-api/anggota.php";
    public static final String LIST_ANGGOTA_URL = "http://smkbifor.sch.id/baojili-api/tes.php";
    public static final String UPDATE_ANGGOTA_URL = "http://smkbifor.sch.id/baojili-api/update_anggota.php";
    public static final String DELETE_ANGGOTA =  "http://smkbifor.sch.id/baojili-api/hapus_anggota.php?username=";
    public static final String UPDATE_ITEM =  "http://smkbifor.sch.id/baojili-api/update_item.php";
    public static final String UPDATE_LOCATION =  "http://smkbifor.sch.id/baojili-api/update_location.php";
    public static final String DELETE_ITEM =  "http://smkbifor.sch.id/baojili-api/hapus_item.php?itemid=";
    public static final String DELETE_LOCATION =  "http://smkbifor.sch.id/baojili-api/hapus_location.php?locationid=";
    public static final String DELETE_ITEM_IN =  "http://smkbifor.sch.id/baojili-api/hapus_item_in.php?iteminid=";
    public static final String DELETE_ITEM_OUT =  "http://smkbifor.sch.id/baojili-api/hapus_item_out.php?itemoutid=";
    public static final String ADD_ITEM_URL = "http://smkbifor.sch.id/baojili-api/add_item.php";
    public static final String ADD_LOCATION_URL = "http://smkbifor.sch.id/baojili-api/add_location.php";
    public static final String LOGIN_QRCODE = "http://smkbifor.sch.id/baojili-api/login_qrcode.php";

    public static final String LIST_ITEM_URL = "http://smkbifor.sch.id/baojili-api/list_item.php";
    public static final String LIST_LOCATION_URL = "http://smkbifor.sch.id/baojili-api/list_location.php";
    public static final String LIST_STOCKID_URL = "http://smkbifor.sch.id/baojili-api/get_stockID.php?itemid=";
    public static final String LIST_ITEMIN_URL = "http://smkbifor.sch.id/baojili-api/list_item_in.php";
    public static final String LIST_ITEMOUT_URL = "http://smkbifor.sch.id/baojili-api/list_item_out.php";
    public static final String LIST_LOCATIONID_URL = "http://smkbifor.sch.id/baojili-api/get_LocationId.php?locationname=";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_ROLE = "role";
    public static final String KEY_QRCODE = "qrcode";
    public static final String KEY_PHOTO_USER = "photo_user";
    public static final String LOGIN_SUCCESS = "success";

    public static final String KEY_ITEMID = "itemid";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_TYPE = "type";
    public static final String KEY_CODE = "code";
    public static final String KEY_SIZE = "size";
    public static final String KEY_PRICE = "price";
    public static final String KEY_COST = "cost";
    public static final String KEY_PHOTO = "photo";

    public static final String KEY_LOCATIONID= "locationid";
    public static final String KEY_NAME_LOCATION = "name_location";
    public static final String KEY_TYPE_LOCATION = "type_location";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";

    public static final String KEY_STOCKID= "stockid";

    public static final String LIST_TOTALSTOCK_BY_LOCATION = "http://smkbifor.sch.id/baojili-api/list_total_stok_by_location.php";
    public static final String LIST_TOTALSTOCK_BY_ITEM = "http://smkbifor.sch.id/baojili-api/list_total_stok_by_item.php";
}
