/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

public class Servicio {
    private String _Sucursal;
    private String _NoCuenta;
    private String _CURP;
    private String _Nombres;
    private String _Apellidos;
    private Double _Saldo;
    private int nodo;

    public String getSucursal() {
        return _Sucursal;
    }

    public void setSucursal(String _Sucursal) {
        this._Sucursal = _Sucursal;
    }

    public String getNoCuenta() {
        return _NoCuenta;
    }

    public void setNoCuenta(String _NoCuenta) {
        this._NoCuenta = _NoCuenta;
    }

    public String getCURP() {
        return _CURP;
    }

    public void setCURP(String _CURP) {
        this._CURP = _CURP;
    }

    public String getNombres() {
        return _Nombres;
    }

    public void setNombres(String _Nombres) {
        this._Nombres = _Nombres;
    }

    public String getApellidos() {
        return _Apellidos;
    }

    public void setApellidos(String _Apellidos) {
        this._Apellidos = _Apellidos;
    }

    public Double getSaldo() {
        return _Saldo;
    }

    public void setSaldo(Double _Saldo) {
        this._Saldo = _Saldo;
    }

    public int getNodo() {
        return nodo;
    }

    public void setNodo(int nodo) {
        this.nodo = nodo;
    }

    public Servicio(String _Sucursal, String _NoCuenta, String _CURP, String _Nombres, String _Apellidos, Double _Saldo, int nodo) {
        this._Sucursal = _Sucursal;
        this._NoCuenta = _NoCuenta;
        this._CURP = _CURP;
        this._Nombres = _Nombres;
        this._Apellidos = _Apellidos;
        this._Saldo = _Saldo;
        this.nodo = nodo;
    }
}
