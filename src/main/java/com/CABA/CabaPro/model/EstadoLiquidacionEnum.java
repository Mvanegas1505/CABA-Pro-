package com.CABA.CabaPro.model;

/**
 * Enumeración que representa los posibles estados de una liquidación.
 */
public enum EstadoLiquidacionEnum {

    /**
     * Estado que indica que la liquidación está pendiente de pago.
     */
    PENDIENTE,

    /**
     * Estado que indica que la liquidación ha sido pagada.
     */
    PAGADA,

    /**
     * Estado que indica que la liquidación ha sido rechazada.
     */
    RECHAZADA
}
