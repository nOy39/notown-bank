import Vue from 'vue'
import App from 'pages/App.vue'
import store from 'store/index'
import router from './pages/router'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue);
new Vue({
    el: '#app',
    render: a=>a(App),
    store,
    router
})
