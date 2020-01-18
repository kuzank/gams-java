package com.gams.api;

public class gamsglobals {
    public static final int maxdim = 20;
    public static final int str_len = 256;
   
    public static final int val_level    = 0;
    public static final int val_marginal = 1;
    public static final int val_lower    = 2;
    public static final int val_upper    = 3;
    public static final int val_scale    = 4;
    public static final int val_max      = 5;

    public static final int sv_und     = 0;
    public static final int sv_na      = 1;
    public static final int sv_pin     = 2;
    public static final int sv_min     = 3;
    public static final int sv_eps     = 4;
    public static final int sv_normal  = 5;
    public static final int sv_acronym = 6;
    public static final int sv_max     = 7;

    public static final int dt_set   = 0;
    public static final int dt_par   = 1;
    public static final int dt_var   = 2;
    public static final int dt_equ   = 3;
    public static final int dt_alias = 4;
    public static final int dt_max   = 5;
    
    public static final double sv_valund     =  1.0E300;   // undefined
    public static final double sv_valna      =  2.0E300;   // not available/applicable
    public static final double sv_valpin     =  3.0E300;   // plus infinity
    public static final double sv_valmin     =  4.0E300;   // minus infinity
    public static final double sv_valeps     =  5.0E300;   // epsilon
    public static final double sv_valacronym = 10.0E300;   // potential/real acronym
}
