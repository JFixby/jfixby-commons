package com.jfixby.cmns.api.util;

import java.util.Comparator;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.CollectionScanner;
import com.jfixby.cmns.api.collections.EditableCollection;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Map;
import com.jfixby.cmns.api.collections.Mapping;
import com.jfixby.cmns.api.collections.Pool;
import com.jfixby.cmns.api.collections.PoolElementsSpawner;
import com.jfixby.cmns.api.collections.Queue;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.cmns.api.collections.StateSwitcher;
import com.jfixby.cmns.api.collections.UtilsComponent;
import com.jfixby.cmns.api.collections.ZxZ_Functuion;
import com.jfixby.cmns.api.components.ComponentInstaller;
import com.jfixby.cmns.api.lambda.λFunction;
import com.jfixby.cmns.api.path.AbsolutePath;
import com.jfixby.cmns.api.path.MountPoint;
import com.jfixby.cmns.api.path.RelativePath;

public class JUtils {

	static private ComponentInstaller<UtilsComponent> componentInstaller = new ComponentInstaller<UtilsComponent>("JUtils");

	public static final void installComponent(UtilsComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final UtilsComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final UtilsComponent component() {
		return componentInstaller.getComponent();
	}

	public static final <T> List<T> newList() {
		return invoke().newList();
	}

	public static final <T> List<T> newList(T... array) {
		return invoke().newList(array);
	}

	public static final <T> List<T> newListFromArray(T[] array) {
		return invoke().newList(array);
	}

	public static final <T> List<T> newList(Collection<? extends T> collection) {
		return invoke().newList(collection);
	}

	public static final <T> List<T> newList(java.util.Collection<? extends T> java_colletion) {
		return invoke().newList(java_colletion);
	}

	public static final <K, V> Map<K, V> newMap() {
		return invoke().newMap();
	}

	public static final <K, V> Map<K, V> newMap(java.util.Map<K, V> java_map) {
		return invoke().newMap(java_map);
	}

	public static final <K, V> Map<K, V> newMap(Mapping<? extends K, ? extends V> other_map) {
		return invoke().newMap(other_map);
	}

	public static final <T> Set<T> newSet() {
		return invoke().newSet();
	}

	public static final <T> Set<T> newSet(T[] array) {
		return invoke().newSet(array);
	}

	public static <T> Set<T> newSet(java.util.Collection<T> java_colletion) {
		return invoke().newSet(java_colletion);
	}

	public static final <T> Set<T> newSet(List<T> array) {
		return invoke().newSet(array);
	}

	public static final RelativePath newRelativePath(String path_string) {
		return invoke().newRelativePath(path_string);
	}

	public static final <T extends MountPoint> AbsolutePath<T> newAbsolutePath(T mount_point, RelativePath relative) {
		return invoke().newAbsolutePath(mount_point, relative);
	}

	public static final <T extends MountPoint> AbsolutePath<T> newAbsolutePath(T mount_point) {
		return invoke().newAbsolutePath(mount_point);
	}

	public static final RelativePath newRelativePath(List<String> steps_list) {
		return invoke().newRelativePath(steps_list);
	}

	public static final RelativePath newRelativePath() {
		return invoke().newRelativePath();
	}

	public static final <T> ZxZ_Functuion<T> newZxZ_Function() {
		return invoke().newZxZ_Function();
	}

	public static <T> Queue<T> newQueue() {
		return invoke().newQueue();
	}

	public static <T> Pool<T> newPool(PoolElementsSpawner<T> spawner) {
		return invoke().newPool(spawner);
	}

	public static <T> void scanCollection(Collection<? extends T> collection, CollectionScanner<T> scanner) {
		invoke().scanCollection(collection, scanner);
	}

	public static boolean equalLists(List<?> A, List<?> B) {
		return invoke().equalLists(A, B);
	}

	public static <T> StateSwitcher<T> newStateSwitcher(T default_state) {
		return invoke().newStateSwitcher(default_state);
	}



	public static List<Float> newList(float[] floats) {
		return invoke().newList(floats);
	}

	public static List<String> split(String input_string, String splitter) {
		return invoke().split(input_string, splitter);
	}

	public static boolean listBeginsWith(Collection<?> list, Collection<?> with) {
		return invoke().beginsWith(list, with);
	}

	static public <Q, P, Cp extends EditableCollection<P>> Cp castCollection(Collection<Q> input, Cp output) {
		return invoke().castCollection(input, output);
	}

	static public <Q, P> List<P> castCollection(Collection<Q> input) {
		return invoke().castCollection(input);
	}

	public static <A, B, X, Y, Mp extends Map<A, B>> Mp castMap(Mapping<X, Y> input, Mp output) {
		return invoke().castMap(input, output);
	}

	public static <A, B, X, Y> Map<A, B> castMap(Mapping<X, Y> input) {
		return invoke().castMap(input);
	}

	public static void checkTrue(boolean flag) {
		invoke().checkTrue(flag);
	}

	public static void checkTrue(String flag_name, boolean flag) {
		invoke().checkTrue(flag_name, flag);
	}

	public static <T> void arrayCopy(Collection<? extends T> source, int source_index, EditableCollection<? super T> destination, int number_of_elements) {
		invoke().arrayCopy(source, source_index, destination, number_of_elements);
	}

	public static <T> λFunction<Collection<T>, Collection<T>> MERGE_SORT(Comparator<? super T> comparator) {
		return invoke().MERGE_SORT(comparator);
	}

	public static <T> λFunction<Collection<T>, Collection<T>> MERGE_SORT() {
		return invoke().MERGE_SORT();
	}
}