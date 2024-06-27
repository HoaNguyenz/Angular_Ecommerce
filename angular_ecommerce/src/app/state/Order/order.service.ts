import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Store } from '@ngrx/store';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, map, of } from 'rxjs';
import { createOrderFailure, createOrderSuccess, getOrderByIdFailure, getOrderByIdSuccess, getOrderHistoryFailure, getOrderHistoryRequest, getOrderHistorySuccess } from './order.action'; // Correct import
import { BASE_API_URL } from '../../config/api';

@Injectable({
    providedIn: 'root',
})
export class OrderService {
    private API_BASE_URL = BASE_API_URL;
    private headers: HttpHeaders;

    constructor(
        private store: Store,
        private http: HttpClient,
        private router: Router,
        private route: ActivatedRoute
    ) {
        this.headers = new HttpHeaders({
            Authorization: `Bearer ${localStorage.getItem('jwt')}`,
            'Content-Type': 'application/json',
        });
    }

    createOrder(reqData: any) {
        const url = `${this.API_BASE_URL}/api/orders/`;

        return this.http.post(url, reqData, { headers: this.headers }).pipe(
            map((data: any) => {
                if (data.id) {
                    this.router.navigate([`/checkout/payment/${data.id}`], {
                        queryParams: { step: '3', order_id: data.id },
                    });
                    console.log('created order - ', data);
                    return createOrderSuccess({ order: data });
                }
                return createOrderFailure({ error: 'Order creation failed' });
            }),
            catchError((error: any) => {
                const errorMessage = error.error?.message || error.message || 'An error occurred';
                return of(createOrderFailure({ error: errorMessage }));
            })
        ).subscribe(action => this.store.dispatch(action));
    }

    getOrderById(orderId: string) {
        const url = `${this.API_BASE_URL}/api/orders/${orderId}`;

        return this.http.get(url, { headers: this.headers }).pipe(
            map((data: any) => {
                console.log('order by id', data);
                return getOrderByIdSuccess({ order: data });
            }),
            catchError((error: any) => {
                const errorMessage = error.error?.message || error.message || 'An error occurred';
                return of(getOrderByIdFailure({ error: errorMessage }));
            })
        ).subscribe(action => this.store.dispatch(action));
    }

    getOrderHistory() {
        const url = `${this.API_BASE_URL}/api/orders/user`;

        return this.http.get<any>(url, { headers: this.headers }).pipe(
            map((data: any) => {
                console.log('order history', data);
                return getOrderHistorySuccess({ orders: data });
            }),
            catchError((error: any) => {
                const errorMessage = error.error?.message || error.message || 'An error occurred';
                return of(getOrderHistoryFailure({ error: errorMessage }));
            })
        ).subscribe(action => this.store.dispatch(action));
    }
}
