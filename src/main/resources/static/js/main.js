import Vue from 'vue'
import App from 'pages/App.vue'
import store from 'store/index'
import router from './router/router'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import moment from 'moment'

moment.locale('en')
Vue.prototype.moment = moment
Vue.use(BootstrapVue);
new Vue({
    el: '#app',
    render: a=>a(App),
    store,
    router
})
//TODO Сделать фронт переводов, покупок
