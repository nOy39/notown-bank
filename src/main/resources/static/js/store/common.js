export default {
    state: {
        test: '',
        error: ''
    },
    mutations: {
        SET_TEST(state, payload) {
            state.test = payload
        },
        SET_ERROR(state, payload) {
            state.error = payload
        }
    },
    getters: {
        getTest(state) {
            return state.test
        },
        getError(state) {
            return state.error
        }
    },
    actions: {
        setTest({commit}, payload) {
            commit('SET_TEST', payload)
        },
        setError({commit}, payload) {
            commit('SET_ERROR', payload)
        }
    }
}
