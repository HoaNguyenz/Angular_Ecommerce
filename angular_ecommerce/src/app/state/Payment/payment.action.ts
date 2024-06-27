import { createAction, props } from "@ngrx/store";

export const createPaymentRequest = createAction(
    '[Payment] Create Payment Request', props<{orderId:any}>()
);
export const createPaymentSuccess = createAction(
    '[Payment] Create Payment Success', props<{payload:any}>()
);
export const createPaymentFailure = createAction(
    '[Payment] Create Payment Failure', props<{payload:any}>()
);