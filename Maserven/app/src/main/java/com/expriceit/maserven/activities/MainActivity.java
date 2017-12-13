package com.expriceit.maserven.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.expriceit.maserven.MaservenApplication;
import com.expriceit.maserven.R;
import com.expriceit.maserven.adaptadores.Adapter;
import com.expriceit.maserven.adaptadores.MItems;
import com.expriceit.maserven.adaptadores.SpinnerAdapter;
import com.expriceit.maserven.entities.ApiResponseProductos;
import com.expriceit.maserven.entities.Items;
import com.expriceit.maserven.entities.ProductoServicios;
import com.expriceit.maserven.entities.Productos;
import com.expriceit.maserven.utils.SharedPreferencesManager;
import com.expriceit.maserven.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FrameLayout Fragment_cli,FrameConsultaCliente,FrameRegistraCliente,FrameNewPedido;
   // LayoutInflater Layout_ConsultarCliente,Layout_RegistraCliente;
    TextView TextMenu_ConsultarCliente, TextMenu_RegistrarCliente, TextCodigoCliente, TextNombreCliente, TextNombreUser,Tv_Descripcion,Tv_Cod_Interno,Tv_Cod_alterno,Tv_PVP,Tv_Stock;
    CheckBox ChkAgregarClinte;
    EditText Edit_in_identificaion;
    Button btn_consulta_cliente, btn_registra_cliente;
    FloatingActionButton Btn_NuevoPedido;
    String PREFERENCIA_INICIO = "maservenapp";
    String KEY_USER = "usuario";
    String DESCRIPCION_PROD = "descripcion";
    ImageButton img_consul_cli, img_reg_cliente,img_add_items;
    Boolean lb_newPedido=false;
    Spinner opciones;
    private ListView ListProductos;
    private Adapter adapter;
    private AlertDialog dialogo_cancel;
    private List<String> lstSource = new ArrayList<>();


    private ListView lvProductos;
    private Call<Items.DatosItem> CallItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         Fragment_cli = (FrameLayout) findViewById(R.id.fragmentcli);
         Fragment_cli.setVisibility(View.INVISIBLE);
        TextMenu_ConsultarCliente = (TextView) findViewById(R.id.textMenuConsultarCliente);
        TextMenu_RegistrarCliente = (TextView) findViewById(R.id.textMenuRegistrarCliente);

        Tv_Descripcion = (TextView) findViewById(R.id.tv_descripcion);
        Tv_Cod_Interno = (TextView) findViewById(R.id.tv_codigo_interno);
        Tv_Cod_alterno = (TextView) findViewById(R.id.tv_codigo_alterno);
        Tv_PVP = (TextView) findViewById(R.id.tv_pvp);
        Tv_Stock = (TextView) findViewById(R.id.tv_stock);


        Edit_in_identificaion = (EditText) findViewById(R.id.edit_in_identificacion);
        TextCodigoCliente = (TextView) findViewById(R.id.textCodCliente);
        TextNombreUser = (TextView) findViewById(R.id.textNombreUsuario);
        TextNombreCliente = (TextView) findViewById(R.id.textNombreCliente);
        ChkAgregarClinte = (CheckBox) findViewById(R.id.checkBoxAgregarCliente);

        FrameConsultaCliente =(FrameLayout) findViewById(R.id.frameConsultaCliente);
        FrameRegistraCliente =(FrameLayout) findViewById(R.id.frameRegistraCliente);
        FrameNewPedido = (FrameLayout) findViewById(R.id.IdNuevoPedido);
        btn_registra_cliente = (Button) findViewById(R.id.btnRegistrarCliente) ;
        btn_consulta_cliente = (Button) findViewById(R.id.btnConsultaCliente) ;
        TextNombreUser.setText(SharedPreferencesManager.getValorEsperado(getApplicationContext(),PREFERENCIA_INICIO,KEY_USER));
        TextCodigoCliente.setVisibility(View.GONE);
        TextNombreCliente.setVisibility(View.GONE);
        ChkAgregarClinte.setVisibility(View.GONE);
        FrameConsultaCliente.setVisibility(View.GONE);
        FrameRegistraCliente.setVisibility(View.GONE);
        FrameNewPedido.setVisibility(View.GONE);

        img_consul_cli = (ImageButton) findViewById(R.id.imgBtnBuscarCliente);
        img_reg_cliente= (ImageButton) findViewById(R.id.imgBtnAddCliente);
        img_add_items =(ImageButton) findViewById(R.id.imgBtnBuscarItem);

        //spinner
        lstSource.add("Código Interno");
        lstSource.add("Código Alterno");
        lstSource.add("Descripción");


       /* ArrayList<MItems> model = new ArrayList<MItems>();
        MItems m = new MItems();

        m.setCodigo_interno("001");
        m.setCodigo_alterno("AFG092");
        m.setDescripcion("MARTILLO");
        m.setPvp("10.00");
        m.setStock("2000");

        model.add(m);
        //-------------
        m = new MItems();
        m.setCodigo_interno("003");
        m.setCodigo_alterno("AFQW92");
        m.setDescripcion("RODILLO");
        m.setPvp("15.00");
        m.setStock("2100");
        model.add(m);

        //-------------
        m = new MItems();
        m.setCodigo_interno("033");
        m.setCodigo_alterno("WRQW92");
        m.setDescripcion("CLAVO 2.5\" ");
        m.setPvp("15.00");
        m.setStock("2100");

        model.add(m);


        adapter = new Adapter(this, model);
        ListProductos = (ListView) findViewById(R.id.ma_lv_lista_productos);
        ListProductos.setAdapter(adapter);*/

        AlertDialogConsultaCliente();
        AlertDialogRegistraCliente();


        img_add_items.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialogAddItems();
            }

        });






        btn_consulta_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                FrameConsultaCliente.setVisibility(View.VISIBLE);
                FrameRegistraCliente.setVisibility(View.GONE);
                if(Edit_in_identificaion==null){
                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "Ingrese Identificación");
                    return;

                }
                if(Edit_in_identificaion.length()<10){
                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "La indetificacion ingresada es invalida.");
                    return;

                }
                //ConsumeConsulta
                TextCodigoCliente.setVisibility(View.VISIBLE);
                TextNombreCliente.setVisibility(View.VISIBLE);
                ChkAgregarClinte.setVisibility(View.VISIBLE);


            }
        });
        TextMenu_ConsultarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                FrameConsultaCliente.setVisibility(View.VISIBLE);
                FrameRegistraCliente.setVisibility(View.GONE);
            }
        });
        TextMenu_RegistrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                FrameConsultaCliente.setVisibility(View.GONE);
                FrameRegistraCliente.setVisibility(View.VISIBLE);
            }
        });

        /**/
        lvProductos = (ListView) findViewById(R.id.lv_productos);
        //  ValidaPinAcceso acceso_pin = MaservenApplication.getApplication().getRestAdapter().create(ValidaPinAcceso.class);

        /* AdaptadorItems adapter = new AdaptadorItems(MainActivity.this, (List<Productos>) call);
                lvProductos.setAdapter(adapter);
                */
        /*http://localhost:8080/MaservenWS/app/ConsultaItemsWS?tipo=DES&cod_int=&cod_alt=&descripcion=bro&linea=
* http://localhost:8080/MaservenWS/app/ConsultaItemsWS?tipo=COI&cod_int=01020020014&cod_alt=&descripcion=&linea=*/
     /*   btn_Consultar_Items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ConsultaItemsWS("COI","01020020014","","","");
                ConsultaItemsWS("DES","","","bro","");
            }
        });
        */


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                //Utils.generarAlerta(MainActivity.this, "Mensaje!", "Nuevo Pedido");

                if(!lb_newPedido){
                    FrameNewPedido.setVisibility(View.VISIBLE);
                    lb_newPedido=true;

                }else{
                    FrameNewPedido.setVisibility(View.GONE);
                    lb_newPedido=false;
                }



            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mis_clientes) {
            Fragment_cli.setVisibility(View.VISIBLE);
          /*  layout_ConsultarCliente.setVisibility(View.GONE);
            layout_RegistraCliente.setVisibility(View.GONE);

            TextMenu_RegistrarCliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                    layout_ConsultarCliente.setVisibility(View.GONE);
                    layout_RegistraCliente.setVisibility(View.VISIBLE);
                }
            });
*/

            // Handle the camera action
        } else if (id == R.id.mis_productos) {
            Fragment_cli.setVisibility(View.GONE);
            FrameConsultaCliente.setVisibility(View.GONE);
            FrameRegistraCliente.setVisibility(View.GONE);

        } else if (id == R.id.estatus_pedido) {
            Fragment_cli.setVisibility(View.GONE);
            FrameConsultaCliente.setVisibility(View.GONE);
            FrameRegistraCliente.setVisibility(View.GONE);

        } else if (id == R.id.cambiar_contrasenia) {
            Fragment_cli.setVisibility(View.GONE);
            FrameConsultaCliente.setVisibility(View.GONE);
            FrameRegistraCliente.setVisibility(View.GONE);

        } else if (id == R.id.cerrar_sesion) {
            Fragment_cli.setVisibility(View.GONE);
            FrameConsultaCliente.setVisibility(View.GONE);
            FrameRegistraCliente.setVisibility(View.GONE);
            //Eliminar Datos antes de Cerrar Sesion
            finish();

        } else if (id == R.id.salir) {
            Fragment_cli.setVisibility(View.GONE);
            FrameConsultaCliente.setVisibility(View.GONE);
            FrameRegistraCliente.setVisibility(View.GONE);
            finish();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




public void AlertDialogConsultaCliente(){
    img_consul_cli.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.consulta_cliente_dialog,null);
            //
            //
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();


        }

    });
}




    public void AlertDialogRegistraCliente(){
        img_reg_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.registra_cliente_dialog,null);
                //
                //
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();


            }

        });
    }
    public void AlertDialogAddItems(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.add_productos_dialog,null);
        opciones = (Spinner) mView.findViewById(R.id.spinnerTipoConsulta);
        SpinnerAdapter sp_adapter = new SpinnerAdapter(lstSource,this);
        opciones.setAdapter(sp_adapter);
        ListProductos = (ListView) mView.findViewById(R.id.add_prod_dialog_lv_lista_items);
        ListProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try{
                   Items.getItems item = (Items.getItems) adapter.getItem(i);
                    Log.w("CallItems", "Esta Seleccionando el siguiente Item:"+item.getCodigo_interno()+" - "+item.getDescripcion());
                    Confirm_addItemLista(item.getDescripcion().toString(),item.getCodigo_alterno().toString(),item.getCodigo_interno().toString(),item.getPvp().toString(),item.getStock().toString());
                }catch (Exception e){
                    e.getStackTrace();
                    Log.w("CallItems", "Exception onItemClick: "+e.getMessage()+"-- msg"+e.getStackTrace());
                }
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
    /*http://localhost:8080/MaservenWS/app/ConsultaItemsWS?tipo=DES&cod_int=&cod_alt=&descripcion=bro&linea=
    * http://localhost:8080/MaservenWS/app/ConsultaItemsWS?tipo=COI&cod_int=01020020014&cod_alt=&descripcion=&linea=*/
    public void ConsultaItemsWS(String tipo, String cod_int, String cod_alt, String descripcion, String linea){
        Items items_servicios = MaservenApplication.getApplication().getRestAdapter().create(Items.class);
        try{
            CallItems = items_servicios.consumeItemsWS(tipo,cod_int,cod_alt,descripcion,linea);
        } catch (IllegalArgumentException e1) {
            //Toast.makeText(getApplicationContext(),"No se puede conectar con la radio.",Toast.LENGTH_LONG).show();
            Log.w("CallItems", "Exception: "+e1.getMessage()+"-- msg"+e1.getStackTrace());
            e1.printStackTrace();
        } catch (IllegalStateException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // call.enqueue(new Callback<ApiResponseAppointments>()
        CallItems.enqueue(new Callback<Items.DatosItem>() {
            @Override
            public void onResponse(Call<Items.DatosItem> call, Response<Items.DatosItem> response) {
                String err = "";
                try {
                    // err = response.errorBody().toString();
                    Log.w("CallItems", "Consultando respuesta" +err );
                    if (err.equalsIgnoreCase("")) {
                        if (response.body() != null) {
                            if (response.isSuccess()) {
                                Items.DatosItem otp = response.body();

                                /*Log.w("CallItems", "response -> "+otp.getMensaje() + "");
                                Log.w("CallItems", "codigo -> "+otp.getCodigo_error() + "");*/
                                // Log.w("Acceso_usuario", "idusuario -> "+Usuario+ "");
                                if (otp.getCodigo_error().equals("1")){
                                    /*Log.w("CallItems", "descripcion -> "+otp.getDescripcion() + "");
                                    Log.w("CallItems", "codigo_interno -> "+otp.getCodigo_interno() + "");
                                    Log.w("CallItems", "codigo_alterno -> "+otp.getCodigo_alterno() + "");
                                    Log.w("CallItems", "PVP -> "+otp.getPvp() + "");
                                    Log.w("CallItems", "STOCK -> "+otp.getStock() + "");*/
                                    if(otp.getData().size()>0){
                                        Log.e("CallItems", otp.getData().get(0).getDescripcion());
                                        //Tv_Descripcion,Tv_Cod_Interno,Tv_Cod_alterno,Tv_PVP,Tv_Stock
                                        String Descripcion= otp.getData().get(0).getDescripcion();
                                        Log.e("CallItems","Descripcion: "+Descripcion);
                                        SharedPreferencesManager.setValor(MainActivity.this,PREFERENCIA_INICIO, Descripcion, DESCRIPCION_PROD);
                                        Log.w("CallItems", "Registrando Descripcion ->"+SharedPreferencesManager.getValorEsperado(MainActivity.this,PREFERENCIA_INICIO,DESCRIPCION_PROD));
                                       // mustraDatos(Descripcion.toString());

                                       ArrayList<MItems> model = new ArrayList<>();
                                        MItems m = new MItems();



                                        for (int s=0; s <otp.getData().size();s++){
                                            m = new MItems();
                                            m.setCodigo_interno(otp.getData().get(s).getCodigo_interno().toString());
                                            m.setCodigo_alterno(otp.getData().get(s).getCodigo_alterno().toString());
                                            m.setDescripcion(otp.getData().get(s).getDescripcion().toString());
                                            m.setPvp(otp.getData().get(s).getPvp().toString().toString());
                                            m.setStock(otp.getData().get(s).getStock().toString());
                                            model.add(m);
                                            Log.w("CallItems", "Descripción Item->"+s+": "+otp.getData().get(s).getDescripcion().toString());


                                        }

                                        //model.add(otp.getData());

                                        adapter = new Adapter(getBaseContext(), otp.getData());
                                        ListProductos.setAdapter(adapter);



                                    }


                                    Log.w("CallItems >>", "ConsultaWS PIN:");
                                    /*Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();*/
                                    //  load_progreesbar.setVisibility(View.INVISIBLE);

                                }else{
                                    Utils.generarAlerta(MainActivity.this, "ALERTA!", "Usuario Incorrecto");
                                    // load_progreesbar.setVisse cayo
                                    // ibility(View.INVISIBLE);

                                }

                            } else {
                                Log.e("CallItems", "Error en el webservice, success false");
                                //load_progreesbar.setVisibility(View.INVISIBLE);

                            }
                        } else {
                            Log.e("CallItems", "Error de web service, no viene json");
                            // load_progreesbar.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        Log.e("CallItems", "Error en el webservice " + err);
                        //load_progreesbar.setVisibility(View.INVISIBLE);
                    }
                    // Log.w("Acceso_usuario", "ERROR: "+err);
                } catch (Exception e) {
                    // err = "";
                    Log.w("CallItems", "Exception: "+e.getMessage()+"-- msg"+e.getStackTrace());
                    //load_progreesbar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Items.DatosItem> call, Throwable t) {
                Log.w("CallItems", "onFailure - "+t.getMessage());
            }
        });

    }

    public void onClickConsultaItemsWS(View v) {

        Log.w("CallItems", "ConsultaItemsWS: ");
        ConsultaItemsWS("DES","","","bro","");
        Log.w("CallItems", "Registrando Descripcion2 ->"+SharedPreferencesManager.getValorEsperado(MainActivity.this,PREFERENCIA_INICIO,DESCRIPCION_PROD));

       // Tv_Descripcion.setText(SharedPreferencesManager.getValorEsperado(MainActivity.this,PREFERENCIA_INICIO,DESCRIPCION_PROD));
    }
    public void mustraDatos(String descripcion){
         Tv_Descripcion.setText(descripcion);

    }
    public void Confirm_addItemLista(String Descripcion, String CodInt, String CodAlt, String Pvp, String lvStock){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.confirm_dialog_items,null);
        //dialog_descripcion  dlg_codigo_alterno dlg_cod_interno dlg_pvp dlg_stock
        TextView dlg_Descripcion = (TextView) mView.findViewById(R.id.dialog_descripcion);
        TextView dlg_CodAlterno = (TextView) mView.findViewById(R.id.dlg_codigo_alterno);
        TextView dlg_CodInterno= (TextView) mView.findViewById(R.id.dlg_cod_interno);
        TextView dlg_PVP = (TextView) mView.findViewById(R.id.dlg_pvp);
        TextView dlg_Stock = (TextView) mView.findViewById(R.id.dlg_stock);

        //
        dlg_Descripcion.setText(Descripcion);
        dlg_CodAlterno.setText(CodAlt);
        dlg_CodInterno.setText(CodInt);
        dlg_PVP.setText(Pvp);
        dlg_Stock.setText(lvStock);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
    void cancelDialog()
    {
        //Now you can either use
        dialogo_cancel.cancel();
        //or dialog.dismiss();
    }
}
