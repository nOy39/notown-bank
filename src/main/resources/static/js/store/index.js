import Vue from 'vue'
import Vuex from 'vuex'
import common from './common'
import auth from './auth'
import user from './user'
import accounts from './accounts'
import history from './history'
import draft from './draftStore'
import test from './test'

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        common, auth, user, accounts, history, test, draft
    }
})
