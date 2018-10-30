export default {
    state: {
        clientAccounts: '',
        currentAcc: '',
    },
    mutations: {
        SET_CLIENT_ACCOUNTS(state, payload) {
            state.clientAccounts = payload
        },
        SET_CURRENT_ACC(state, payload) {
            state.currentAcc = payload
        }
    },
    getters: {
        getClientAccounts(state) {
            return state.clientAccounts
        },
        getCurrentAcc(state) {
                return state.currentAcc
        }
    },
    actions: {
        clearAccounts({commit}) {
            commit('SET_ACCOUNTS', null)
            commit('SET_CURRENT_ACC', null)
        },
        setCurrentAcc({commit}, payload) {
            commit('SET_CURRENT_ACC', payload)
        }
    }
}
