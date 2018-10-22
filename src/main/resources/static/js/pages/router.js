import Vue from 'vue'
import Router from 'vue-router'
import Test from 'pages/Test.vue'
import Main from 'pages/Main.vue'
import Account from 'pages/PersonAccount.vue'

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
            name: 'Account',
            component: Account
        }
    ]
})
