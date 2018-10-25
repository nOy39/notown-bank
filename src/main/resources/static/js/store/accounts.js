export default {
    state: {
        accounts: '',
        currentAcc: '',
    },
    mutations: {
        SET_ACCOUNTS(state, payload) {
            state.accounts = payload
        },
        SET_CURRENT_ACC(state, payload) {
            state.currentAcc = payload
        }
    },
    getters: {
        getAccounts(state) {
            return state.accounts
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
