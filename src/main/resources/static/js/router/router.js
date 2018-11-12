import Vue from 'vue'
import Router from 'vue-router'
import ClientGuards from './auth-clients'
import Test from 'pages/Test.vue'
import Main from 'pages/Main.vue'
import ClientCabinet from 'pages/account/ClientCabinet.vue'
import ManagerCabinet from 'pages/ManagerCabinet.vue'
import Account from 'pages/account/AccountPage.vue'
import TestSub from 'pages/TestSub.vue'
import Current from 'pages/account/Current.vue'
import History from 'pages/account/HistoryPage.vue'
import Payment from 'pages/account/Payment.vue'

Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'Test',
            component: Test
        },
        {
            path: '/welcome',
            name: 'Main',
            component: Main
        },
        {
            path: '/browse',
            name: 'ClientCabinet',
            component: ClientCabinet,
            beforeEnter: ClientGuards
        },
        {
            path: '/current',
            name: 'Current',
            component: Current,
            beforeEnter: ClientGuards
        },
        {
            path: '/manager',
            name: 'ManagerCabinet',
            component: ManagerCabinet
        },
        {
            path: '/account/:id',
            name: 'Account',
            props: true,
            component: Account,
            beforeEnter: ClientGuards
        },
        {
            path: '/history/:id',
            name: 'History',
            props: true,
            component: History,
            beforeEnter: ClientGuards
        },
        {
            path: '/payment',
            name: 'Payment',
            props: true,
            component: Payment
        },
        {
            path: '/:id',
            component: TestSub,
            name: 'TestSub',
            props: true,
        }

    ]
})
