import java.util.concurrent.atomic.AtomicReference;

class Budget {
    private final int amount;

    public Budget(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}

public class BudgetServiceAtomicReference {

    private final AtomicReference<Budget> budget =
            new AtomicReference<>(new Budget(1000));

    public void reserve(int value) {

        while (true) {
            Budget current = budget.get();

            if (current.getAmount() < value) {
                throw new RuntimeException("Insufficient funds");
            }

            Budget updated =
                    new Budget(current.getAmount() - value);

            if (budget.compareAndSet(current, updated)) {
                break; // success
            }
        }
    }
}
