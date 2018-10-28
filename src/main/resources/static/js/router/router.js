import Vue from 'vue'
import Router from 'vue-router'
import ClientGuards from './auth-clients'
import Test from 'pages/Test.vue'
import Main from 'pages/Main.vue'
import ClientCabinet from 'pages/ClientCabinet.vue'
import ManagerCabinet from 'pages/ManagerCabinet.vue'
import Account from 'pages/Account.vue'

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
            path: '/account',
            name: 'ClientCabinet',
            component: ClientCabinet,
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
    ]
})
