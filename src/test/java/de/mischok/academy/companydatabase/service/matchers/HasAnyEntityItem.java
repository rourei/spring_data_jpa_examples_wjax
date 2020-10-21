package de.mischok.academy.companydatabase.service.matchers;

import de.mischok.academy.companydatabase.domain.WithId;
import de.mischok.academy.companydatabase.service.QueryServiceTest;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HasAnyEntityItem extends TypeSafeMatcher<List<? extends WithId>> {

    private final List<? extends WithId> current;

    @Override
    protected boolean matchesSafely(@NonNull final List<? extends WithId> other) {
        if (current == null) {
            return false;
        } else {
            return current.stream()
                    .anyMatch(cur -> other.stream().anyMatch(o -> o.getClass().equals(cur.getClass()) && o.getId().equals(cur.getId())));
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("list does not contain any of entities " + current.toString());
    }

    public static Matcher<List<? extends WithId>> hasAnyEntityItem(WithId... current) {
        return new HasAnyEntityItem(List.of(current));
    }
}
