package br.ufpr.dinf.gres.core.jmetal4.operators;

import br.ufpr.dinf.gres.core.jmetal4.operators.mutation.*;

/**
 * Feature mutation operators enum
 * <p>
 * {@link FeatureMutation Feature Mutation,}
 * {@link MoveMethodMutation Move Method Mutation,}
 * {@link MoveAttributeMutation Move Attribute Mutation,}
 * {@link MoveOperationMutation Move Operation Mutation,}
 * {@link AddClassMutation Add Class Mutation,}
 * {@link AddManagerClassMutation Add Manager Class Mutation}
 */
public enum FeatureMutationOperators implements IFeatureMutationOperators {

    FEATURE_MUTATION {
        @Override
        public IMutationOperator getOperator() {
            return new FeatureMutation();
        }
    },
    MOVE_METHOD_MUTATION {
        @Override
        public IMutationOperator getOperator() {
            return new MoveMethodMutation();
        }
    },
    MOVE_ATTRIBUTE_MUTATION {
        @Override
        public IMutationOperator getOperator() {
            return new MoveAttributeMutation();
        }
    },
    MOVE_OPERATION_MUTATION {
        @Override
        public IMutationOperator getOperator() {
            return new MoveOperationMutation();
        }
    },
    ADD_CLASS_MUTATION {
        @Override
        public IMutationOperator getOperator() {
            return new AddClassMutation();
        }
    },
    ADD_MANAGER_CLASS_MUTATION {
        @Override
        public IMutationOperator getOperator() {
            return new AddManagerClassMutation();
        }
    },
    DESIGN_PATTERNS {
        @Override
        public IMutationOperator getOperator() {
            return null;
        }
    }
}
