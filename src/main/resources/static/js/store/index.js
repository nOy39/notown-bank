import Vue from 'vue'
import Vuex from 'vuex'
import common from './common'
import user from './user'
import accounts from './accounts'
import history from './history'

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        common, user, accounts, history
    }
})
