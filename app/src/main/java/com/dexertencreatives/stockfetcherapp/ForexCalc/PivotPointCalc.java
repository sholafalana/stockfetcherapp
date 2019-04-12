package com.dexertencreatives.stockfetcherapp.ForexCalc;


public class PivotPointCalc {


    public double CalculateStandardPivot(double prevHigh, double prevLow, double prevClose) {
        double pivot = (prevHigh + prevLow + prevClose) / 3;
        return pivot;
    }

    public double CalculateStandardR1(double prevHigh, double prevLow, double prevClose) {
        double R1 = (2 * CalculateStandardPivot(prevHigh, prevLow, prevClose)) - prevLow;
        return R1;
    }

    public double CalculateStandardR2(double prevHigh, double prevLow, double prevClose) {
        double R2 = CalculateStandardPivot(prevHigh, prevLow, prevClose) + (prevHigh - prevLow);
        return R2;
    }

    public double CalculateStandardR3(double prevHigh, double prevLow, double prevClose) {
        double R3 = prevHigh + 2 * (CalculateStandardPivot(prevHigh, prevLow, prevClose) - prevLow);
        return R3;
    }

    public double CalculateStandardS1(double prevHigh, double prevLow, double prevClose) {
        double S1 = (2 * CalculateStandardPivot(prevHigh, prevLow, prevClose)) - prevHigh;
        return S1;
    }

    public double CalculateStandardS2(double prevHigh, double prevLow, double prevClose) {
        double S2 = CalculateStandardPivot(prevHigh, prevLow, prevClose) - (prevHigh - prevLow);
        return S2;
    }

    public double CalculateStandardS3(double prevHigh, double prevLow, double prevClose) {
        double S3 = prevLow - (2 * (prevHigh - CalculateStandardPivot(prevHigh, prevLow, prevClose)));
        return S3;
    }


    // woodie pivot calculator
    public double CalculateWoodiePivot(double prevHigh, double prevLow, double currOpen) {
        double pivot = (prevHigh + prevLow + currOpen + currOpen) / 4;
        return pivot;
    }

    public double CalculateWoodieR1(double prevHigh, double prevLow, double currOpen) {
        double R1 = (2 * CalculateWoodiePivot(prevHigh, prevLow, currOpen)) - prevLow;
        return R1;
    }

    public double CalculateWoodieR2(double prevHigh, double prevLow, double currOpen) {
        double R2 = CalculateWoodiePivot(prevHigh, prevLow, currOpen) + (prevHigh - prevLow);
        return R2;
    }

    public double CalculateWoodieR3(double prevHigh, double prevLow, double currOpen) {
        double R3 = prevHigh + 2 * (CalculateWoodiePivot(prevHigh, prevLow, currOpen) - prevLow);
        return R3;
    }


    public double CalculateWoodieS1(double prevHigh, double prevLow, double currOpen) {
        double S1 = (2 * CalculateWoodiePivot(prevHigh, prevLow, currOpen)) - prevHigh;
        return S1;
    }

    public double CalculateWoodieS2(double prevHigh, double prevLow, double currOpen) {
        double S2 = CalculateWoodiePivot(prevHigh, prevLow, currOpen) - (prevHigh - prevLow);
        return S2;
    }

    public double CalculateWoodieS3(double prevHigh, double prevLow, double currOpen) {
        double S3 = prevLow - 2 * (prevHigh - CalculateWoodiePivot(prevHigh, prevLow, currOpen));
        return S3;
    }


    // Camarilla pivot calculator
    public double CalculateCamarillaPivot(double prevHigh, double prevLow, double currOpen) {
        double pivot = (prevHigh + prevLow + currOpen) / 3;
        return pivot;
    }

    public double CalculateCamarillaR1(double prevHigh, double prevLow, double currOpen) {
        double R1 = currOpen + ((prevHigh - prevLow) * 1.0833);
        return R1;
    }

    public double CalculateCamarillaR2(double prevHigh, double prevLow, double currOpen) {
        double R2 = currOpen + ((prevHigh - prevLow) * 1.1666);
        return R2;
    }

    public double CalculateCamarillaR3(double prevHigh, double prevLow, double currOpen) {
        double R3 = currOpen + ((prevHigh - prevLow) * 1.2500);
        return R3;
    }


    public double CalculateCamarillaS1(double prevHigh, double prevLow, double currOpen) {
        double S1 = currOpen - ((prevHigh - prevLow) * 1.0833);
        return S1;
    }

    public double CalculateCamarillaS2(double prevHigh, double prevLow, double currOpen) {
        double S2 = currOpen - ((prevHigh - prevLow) * 1.1666);
        return S2;
    }

    public double CalculateCamarillaS3(double prevHigh, double prevLow, double currOpen) {
        double S3 = currOpen - ((prevHigh - prevLow) * 1.2500);
        return S3;
    }


    // Fibo pivot calculator
    public double CalculateFiboPivot(double prevHigh, double prevLow, double currOpen) {
        double pivot = (prevHigh + prevLow + currOpen) / 3;
        return pivot;
    }

    public double CalculateFiboR1(double prevHigh, double prevLow, double currOpen) {
        double R1 = CalculateFiboPivot(prevHigh, prevLow, currOpen) + ((prevHigh - prevLow) * 0.382);
        return R1;
    }

    public double CalculateFiboR2(double prevHigh, double prevLow, double currOpen) {
        double R2 = CalculateFiboPivot(prevHigh, prevLow, currOpen) + ((prevHigh - prevLow) * 0.618);
        return R2;
    }

    public double CalculateFiboR3(double prevHigh, double prevLow, double currOpen) {
        double R3 = CalculateFiboPivot(prevHigh, prevLow, currOpen) + ((prevHigh - prevLow) * 1.000);
        return R3;
    }


    public double CalculateFiboS1(double prevHigh, double prevLow, double currOpen) {
        double S1 = CalculateFiboPivot(prevHigh, prevLow, currOpen) - ((prevHigh - prevLow) * 0.382);
        return S1;
    }

    public double CalculateFiboS2(double prevHigh, double prevLow, double currOpen) {
        double S2 = CalculateFiboPivot(prevHigh, prevLow, currOpen) - ((prevHigh - prevLow) * 0.618);
        return S2;
    }

    public double CalculateFiboS3(double prevHigh, double prevLow, double currOpen) {
        double S3 = CalculateFiboPivot(prevHigh, prevLow, currOpen) - ((prevHigh - prevLow) * 1.000);
        return S3;
    }


    //  DeMarks Pivot Point

    public double CalculateDemarkPivot(double prevHigh, double prevLow, double prevClose, double prevOpen) {
        double xfactor = 0;
        if (prevClose < prevOpen) {
            xfactor = prevHigh + (2 * prevLow) + prevClose;
        }

        if (prevClose > prevOpen) {
            xfactor = (2 * prevHigh) + prevLow + prevClose;
        }

        if (prevClose == prevOpen) {
            xfactor = prevHigh + prevLow + (2 * prevClose);
        }

        double pivot = xfactor / 4;
        return pivot;
    }

    public double CalculateDemarkR1(double prevHigh, double prevLow, double prevClose, double prevOpen) {
        double xfactor = 0;
        if (prevClose < prevOpen) {
            xfactor = prevHigh + (2 * prevLow) + prevClose;
        }

        if (prevClose > prevOpen) {
            xfactor = (2 * prevHigh) + prevLow + prevClose;
        }

        if (prevClose == prevOpen) {
            xfactor = prevHigh + prevLow + (2 * prevClose);
        }

        double R1 = (xfactor / 2) - prevLow;
        return R1;
    }


    public double CalculateDemarkS1(double prevHigh, double prevLow, double prevClose, double prevOpen) {
        double xfactor = 0;
        if (prevClose < prevOpen) {
            xfactor = prevHigh + (2 * prevLow) + prevClose;
        }

        if (prevClose > prevOpen) {
            xfactor = (2 * prevHigh) + prevLow + prevClose;
        }

        if (prevClose == prevOpen) {
            xfactor = prevHigh + prevLow + (2 * prevClose);
        }

        double S1 = (xfactor / 2) - prevHigh;
        return S1;
    }


}
