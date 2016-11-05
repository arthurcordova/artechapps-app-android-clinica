package br.com.artechapps.app.utils;

/**
 * Created by arthurcordova on 7/8/16.
 */
public class EndPoints extends Webservice{

    public static final String LOGIN = SERVER.concat("/login");

    public static final String NEW_USER = SERVER.concat("/cadcliente");

    public static final String MESSAGES = SERVER.concat("/msgsporcliente/");

    public static final String MESSAGES_DELETE = SERVER.concat("/excluirmsg/");

    public static final String PRODUCTS = SERVER.concat("/procedimentos/");

    public static final String MONEY_PER_CLIENT = SERVER.concat("/orcporcliente/");

    public static final String SCHEDULES = SERVER.concat("/agendamentos/");

    public static final String DOCTORS = SERVER.concat("/medicos/");

    public static final String TIME = SERVER.concat("/horarios/");

    public static final String CONFIRM_APPOINTMENT = SERVER.concat("/realizaragendamento/");

    public static final String CANCEL_APPOINTMENT = SERVER.concat("/cancelaragendamento/");

    public static final String CONFIRM_CART = SERVER.concat("/cadorcamento/");

    public static final String CONFIRM_CART_PRODUCTS = SERVER.concat("/cadprodorcamento/");


}
