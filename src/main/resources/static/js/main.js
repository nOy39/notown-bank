import Vue from 'vue'
import App from 'pages/App.vue'
import store from 'store'
import router from 'vue-router'

new Vue({
    el: '#app',
    render: a=>a(App),
    store,
    router
})
