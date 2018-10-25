export default {
    state: {
        history: ''
    },
    mutations: {
        SET_HISTORY(state, payload) {
            state.history = payload
        }
    },
    getters: {
        getHistory(state) {
            return state.history
        }
    },
    actions: {
        setHistory({commit}, payload) {
            commit('SET_HISTORY', payload)
        }
    }
}
