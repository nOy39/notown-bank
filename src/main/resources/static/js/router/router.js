import Vue from 'vue'
import Router from 'vue-router'
import ClientGuards from './auth-clients'
import Test from 'pages/Test.vue'
import Main from 'pages/Main.vue'
import ClientCabinet from 'pages/ClientCabinet.vue'
import ManagerCabinet from 'pages/ManagerCabinet.vue'
import Account from 'pages/Account.vue'
import TestSub from 'pages/TestSub.vue'
import Browse from 'pages/Browse.vue'

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
            path: '/:id',
            component: TestSub,
            name: 'TestSub',
            props: true,
        }

    ]
})
