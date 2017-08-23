package com.anjoyo.xyl.run.hookgps;

import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

import java.util.ArrayList;

import de.robv.android.xposed.XposedHelpers;

public class i {
    public static CellLocation a(int arg6, int arg7, int arg8, int arg9) {
        GsmCellLocation v0_1=null;
        if(arg9 == 2) {
            CdmaCellLocation v0 = new CdmaCellLocation();
            v0.setCellLocationData(arg7, 0, 0, arg8, arg6);
        }
        else {
            v0_1 = new GsmCellLocation();
            v0_1.setLacAndCid(arg6, arg7);
        }

        return ((CellLocation)v0_1);
    }

    public static ArrayList a(int arg10, int arg11, int arg12, int arg13, int arg14, int arg15) {
        Object[] v3;
        Class v1;
        Object v0;
        int v9 = 4;
        int v8 = 3;
        int v5 = 2;
        ArrayList v2 = new ArrayList();
        switch(arg15) {
            case 1:
            case 2: {
                v0 = XposedHelpers.newInstance(CellInfoGsm.class, new Object[0]);
                v1 = CellIdentityGsm.class;
                v3 = new Object[v9];
                v3[0] = Integer.valueOf(arg10);
                v3[1] = Integer.valueOf(arg11);
                v3[v5] = Integer.valueOf(arg12);
                v3[v8] = Integer.valueOf(arg13);
                XposedHelpers.callMethod(v0, "setCellIdentity", new Object[]{XposedHelpers.newInstance(v1, v3)});
                v1 = CellSignalStrengthGsm.class;
                v3 = new Object[v5];
                v3[0] = Integer.valueOf(26);
                v3[1] = Integer.valueOf(1);
                XposedHelpers.callMethod(v0, "setCellSignalStrength", new Object[]{XposedHelpers.newInstance(v1, v3)});
                v2.add(v0);
                break;
            }
            case 13: {
                v0 = XposedHelpers.newInstance(CellInfoLte.class, new Object[0]);
                v1 = CellIdentityLte.class;
                v3 = new Object[5];
                v3[0] = Integer.valueOf(arg10);
                v3[1] = Integer.valueOf(arg11);
                v3[v5] = Integer.valueOf(arg13);
                v3[v8] = Integer.valueOf(300);
                v3[v9] = Integer.valueOf(arg12);
                XposedHelpers.callMethod(v0, "setCellIdentity", new Object[]{XposedHelpers.newInstance(v1, v3)});
                v1 = CellSignalStrengthLte.class;
                v3 = new Object[6];
                v3[0] = Integer.valueOf(12);
                v3[1] = Integer.valueOf(-90);
                v3[v5] = Integer.valueOf(-12);
                v3[v8] = Integer.valueOf(9);
                v3[v9] = Integer.valueOf(50);
                v3[5] = Integer.valueOf(9);
                XposedHelpers.callMethod(v0, "setCellSignalStrength", new Object[]{XposedHelpers.newInstance(v1, v3)});
                v2.add(v0);
                break;
            }
            case 4:
            case 5:
            case 6:
            case 7:
            case 12:
            case 14: {
                v0 = XposedHelpers.newInstance(CellInfoCdma.class, new Object[0]);
                v1 = CellIdentityCdma.class;
                v3 = new Object[5];
                v3[0] = Integer.valueOf(arg12);
                v3[1] = Integer.valueOf(arg14);
                v3[v5] = Integer.valueOf(arg13);
                v3[v8] = Integer.valueOf(0);
                v3[v9] = Integer.valueOf(0);
                XposedHelpers.callMethod(v0, "setCellIdentity", new Object[]{XposedHelpers.newInstance(v1, v3)});
                v1 = CellSignalStrengthCdma.class;
                v3 = new Object[5];
                v3[0] = Integer.valueOf(-73);
                v3[1] = Integer.valueOf(-89);
                v3[v5] = Integer.valueOf(-63);
                v3[v8] = Integer.valueOf(-79);
                v3[v9] = Integer.valueOf(8);
                XposedHelpers.callMethod(v0, "setCellSignalStrength", new Object[]{XposedHelpers.newInstance(v1, v3)});
                v2.add(v0);
                break;
            }
            case 3:
            case 8:
            case 9:
            case 10:
            case 15: {
                v0 = XposedHelpers.newInstance(CellInfoWcdma.class, new Object[0]);
                v1 = CellIdentityWcdma.class;
                v3 = new Object[5];
                v3[0] = Integer.valueOf(arg10);
                v3[1] = Integer.valueOf(arg11);
                v3[v5] = Integer.valueOf(arg12);
                v3[v8] = Integer.valueOf(arg13);
                v3[v9] = Integer.valueOf(300);
                XposedHelpers.callMethod(v0, "setCellIdentity", new Object[]{XposedHelpers.newInstance(v1, v3)});
                v1 = CellSignalStrengthWcdma.class;
                v3 = new Object[v5];
                v3[0] = Integer.valueOf(25);
                v3[1] = Integer.valueOf(1);
                XposedHelpers.callMethod(v0, "setCellSignalStrength", new Object[]{XposedHelpers.newInstance(v1, v3)});
                v2.add(v0);
                break;
            }
        }

        return v2;
    }
}
