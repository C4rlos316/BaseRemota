package com.prueba.baseremota.Entidades;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySinglenton {

    private static VolleySinglenton instanceVolley;
    private RequestQueue requestQueue;
    private Context contexto;

    private VolleySinglenton(Context context) {

        contexto=context;
        requestQueue=getRequestQueue();
    }

    public RequestQueue getRequestQueue() {


        if (requestQueue==null){

            requestQueue= Volley.newRequestQueue(contexto.getApplicationContext());
        }

        return requestQueue;
    }

    public static synchronized VolleySinglenton getInstanceVolley(Context context) {

        if (instanceVolley==null){
            instanceVolley=new VolleySinglenton(context);

        }

        return instanceVolley;
    }

    public <T> void addToRequestQueue(Request<T>request){

        getRequestQueue().add(request);

    }
}
