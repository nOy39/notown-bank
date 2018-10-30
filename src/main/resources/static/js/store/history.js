export default {
    state: {
        history: '',
        shortHistory:''
    },
    mutations: {
        SET_HISTORY(state, payload) {
            state.history = payload
        },
        SET_SHORT_HISTORY(state, payload) {
            state.shortHistory = payload
        }
    },
    getters: {
        getHistory(state) {
            return state.history
        },
        getShortHistory(state) {
            return state.shortHistory
        }
    },
    actions: {
        setHistory({commit}, payload) {
            commit('SET_HISTORY', payload)
        },
        setShortHistory({commit}, payload) {
            commit('SET_SHORT_HISTORY', payload)
        }
    }
}
