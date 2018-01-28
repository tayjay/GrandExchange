package com.tayjay.grandexchange.lib;

/**
 * Created by tayjay on 2018-01-24.
 */
public class Ref
{
    //Packet IDs
    public static final int TEST_PACKET = 0;
    public static final int GET_PLAYER_PACKET = 2;
    public static final int GET_ITEMS_PACKET = 4;
    public static final int CREATE_PLAYER_PACKET = 6;
    public static final int GET_ITEM_IN_SLOT_PACKET = 8;
    public static final int REQUEST_ITEM_PACKET = 10;
    public static final int OFFER_ITEM_PACKET = 12;
    public static final int CONFIRM_ITEM_VALID_PACKET = 14;



    //Packet Types
    public static final String REQUEST = "request";
    public static final String RESPONSE = "response";
    public static final String ERROR = "error";

    //Packet Values
    public static final String USERNAME = "username";
    public static final String PLAYER_ID = "p_id";
    public static final String UUID = "uuid";
    public static final String SLOT = "slot";
    public static final String ITEMS = "items";
    public static final String EXCHANGE_ITEM = "exchange_item";
    public static final String VALID = "valid";

    //Database references
    public static final String DISPLAY_NAME = "disp_name";
    public static final String REGISTRY_NAME = "reg_name";
    public static final String MOD_NAME = "mod_name";
    public static final String MC_VERSION = "mc_version";
    public static final String ITEM_NBT = "nbt";



}
