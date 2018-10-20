import Vue from 'vue'
import App from 'pages/App.vue'
import store from 'index'
import router from './pages/router'

new Vue({
    el: '#app',
    render: a=>a(App),
    store,
    router
})
