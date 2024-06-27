
import { createReducer, on } from "@ngrx/store";
import { createOrderFailure, createOrderRequest, createOrderSuccess, getOrderByIdFailure, getOrderByIdRequest, getOrderByIdSuccess, getOrderHistoryFailure, getOrderHistoryRequest, getOrderHistorySuccess } from "./order.action";

export interface OrderState{
    orders: any[],
    loading: boolean,
    error: string | null,
    order: any | null
}

const initialState: OrderState = {
    orders: [],
    loading: false,
    error: null,
    order: null
};

export const orderReducer = createReducer(
    initialState,

    on(createOrderRequest,
        getOrderByIdRequest,
        getOrderHistoryRequest,
        (state)=>({
        ...state,
        loading: true,
        error: null
    })),
    on(createOrderSuccess,(state, {order})=>({
        ...state,
        loading: false,
        order
    })),
    on(createOrderFailure,
        getOrderByIdFailure,
        getOrderHistoryFailure,
        (state, {error})=>({
        ...state,
        loading: false,
        error
    })),

   
    on(getOrderByIdSuccess,(state, {order})=>({
        ...state,
        loading: false,
        order
    })),

    on(getOrderHistorySuccess,(state, {orders})=>({
        ...state,
        loading: false,
        orders
    })),

)

