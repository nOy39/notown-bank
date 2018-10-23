import Vue from 'vue'
import Router from 'vue-router'
import Test from 'pages/Test.vue'
import Main from 'pages/Main.vue'
import ClientCabinet from 'pages/ClientCabinet.vue'
import ManagerCabinet from 'pages/ManagerCabinet.vue'

Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/test',
            name: 'Test',
            component: Test
        },
        {
            path: '/',
            name: 'Main',
            component: Main
        },
        {
            path: '/account',
            name: 'ClientCabinet',
            component: ClientCabinet
        },
        {
            path: '/manager',
            name: 'ManagerCabinet',
            component: ManagerCabinet
        },
    ]
})
