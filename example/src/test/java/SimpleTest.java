import com.github.gwcult.treegen.Utils;
import com.github.gwcult.treegen.generated.UserNode;
import com.github.gwcult.treegen.tree.ValueNode;
import org.assertj.core.api.WithAssertions;
import com.github.gwcult.treegen.example.User;
import org.junit.jupiter.api.Test;

public class SimpleTest implements WithAssertions {
    private final User user = new User();
    private final UserNode userNode = new UserNode.Builder().build(user);

    @Test
    public void indexTest() {
        String item = userNode.emails().get(0).get(1).getValue();
        assertThat(item).isEqualTo("b");
    }

    @Test
    public void listPathTest() {
        ValueNode<String> emailItemNode = userNode.emails().get(0).get(1);
        assertThat(Utils.getPath(emailItemNode)).isEqualTo("emails[0][1]");
    }

    @Test
    public void pathTest() {
        ValueNode<String> subUserName = userNode.subUser().name();
        assertThat(Utils.getPath(subUserName)).isEqualTo("subUser.name");
    }
}
